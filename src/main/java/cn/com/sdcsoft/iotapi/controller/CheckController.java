package cn.com.sdcsoft.iotapi.controller;


import cn.com.sdcsoft.iotapi.entity.Sim;
import cn.com.sdcsoft.iotapi.mapper.SimMapper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping(value = "/check")
public class CheckController {

    @Autowired
    private SimMapper simMapper;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/find")
    public void check() throws IOException {

        List<Sim> simList=  simMapper.findSim(new Sim());
        for(int i=0;i<simList.size();i++){
            String simSql="";
            String updateSql="";
            Request cookieRequest = new Request.Builder()
                    .url("https://api.iot.10086.cn/v2/querycardlifecycle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000432&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+simList.get(i).getSimNo())
                    .get()
                    .build();
            Response execute = okHttpClient.newCall(cookieRequest).execute();
            JSONObject result = JSONObject.parseObject(execute.body().string());
            execute.close();
            JSONArray result1 = JSONObject.parseArray(result.get("result").toString());

            if(result.get("status").equals("0")&!result1.getJSONObject(0).get("lifecycle").equals(simList.get(i).getLifecycle())){
                simSql+="update Sim set Lifecycle="+result1.getJSONObject(0).get("lifecycle");
                updateSql="insert into Updatehistory (SimNo,RawState,NowState,CreateDatetime,Type) values ( '"+simList.get(i).getSimNo()+"' ,"+simList.get(i).getLifecycle()+","+result1.getJSONObject(0).get("lifecycle")+",now(),1)";
                rabbitTemplate.convertAndSend(updateSql);
                 cookieRequest = new Request.Builder()
                        .url("https://api.iot.10086.cn/v2/gprsrealsingle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000008&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+simList.get(i).getSimNo())
                        .get()
                        .build();
                execute = okHttpClient.newCall(cookieRequest).execute();
                JSONObject result2 = JSONObject.parseObject(execute.body().string());
                JSONArray result3 = JSONObject.parseArray(result2.get("result").toString());

                if(result2.get("status").equals("0")&!result3.getJSONObject(0).get("GPRSSTATUS").equals(simList.get(i).getState())){

                    if(result3.getJSONObject(0).get("GPRSSTATUS").equals("")){
                        simSql+=",State=00";
                        updateSql="insert into Updatehistory (SimNo,RawState,NowState,CreateDatetime,Type) values ( '"+simList.get(i).getSimNo()+"' ,"+simList.get(i).getState()+",00,now(),2)";
                    }else{
                        simSql+=",State="+result3.getJSONObject(0).get("GPRSSTATUS");
                        updateSql="insert into Updatehistory (SimNo,RawState,NowState,CreateDatetime,Type) values ( '"+simList.get(i).getSimNo()+"' ,"+simList.get(i).getState()+","+result3.getJSONObject(0).get("GPRSSTATUS")+",now(),2)";
                    }
                    simSql+=" where Id="+simList.get(i).getId();
                    rabbitTemplate.convertAndSend(updateSql);
                    rabbitTemplate.convertAndSend(simSql);
                }
            }

        }

    }
}
