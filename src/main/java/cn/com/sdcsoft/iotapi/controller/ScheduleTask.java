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
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Component
@Configuration
@EnableScheduling
public class ScheduleTask {

    @Autowired
    private SimMapper simMapper;

    @Autowired
    private OkHttpClient okHttpClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedDelay=1800000)
    public void check() throws IOException {
        System.out.println(new Date());
        List<Sim> simList=  simMapper.findSim(new Sim());
        for(int i=0;i<simList.size();i++){
            String simDate="";
            String updateDate="";
            Request cookieRequest = new Request.Builder()
                    .url("https://api.iot.10086.cn/v2/querycardlifecycle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000432&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+simList.get(i).getSimNo())
                    .get()
                    .build();
            Response execute = okHttpClient.newCall(cookieRequest).execute();
            JSONObject result = JSONObject.parseObject(execute.body().string());
            execute.close();
            JSONArray result1 = JSONObject.parseArray(result.get("result").toString());


            if(result.get("status").equals("0")){
                if(!result1.getJSONObject(0).get("lifecycle").equals(simList.get(i).getLifecycle())){
                    simDate="update_"+simList.get(i).getId()+"_Lifecycle_"+result1.getJSONObject(0).get("lifecycle");
                    updateDate="insert_"+simList.get(i).getSimNo()+"_"+simList.get(i).getLifecycle()+"_"+result1.getJSONObject(0).get("lifecycle")+"_1";
                    rabbitTemplate.convertAndSend(updateDate);
                    cookieRequest = new Request.Builder()
                            .url("https://api.iot.10086.cn/v2/gprsrealsingle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000008&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+simList.get(i).getSimNo())
                            .get()
                            .build();
                    execute = okHttpClient.newCall(cookieRequest).execute();
                    JSONObject result2 = JSONObject.parseObject(execute.body().string());
                    JSONArray result3 = JSONObject.parseArray(result2.get("result").toString());

                    if(result2.get("status").equals("0")){
                        if(!result3.getJSONObject(0).get("GPRSSTATUS").equals(simList.get(i).getState())){
                            if(result3.getJSONObject(0).get("GPRSSTATUS").equals("")){
                                simDate+="_State_00";
                                //updateDate="insert_"+simList.get(i).getSimNo()+"_"+simList.get(i).getState()+"_00_2";

                            }else{
                                simDate+="_State_"+result3.getJSONObject(0).get("GPRSSTATUS");
                                updateDate="insert_"+simList.get(i).getSimNo()+"_"+simList.get(i).getState()+"_"+result3.getJSONObject(0).get("GPRSSTATUS")+"_2";
                                rabbitTemplate.convertAndSend(updateDate);
                            }
                            rabbitTemplate.convertAndSend(simDate);
                        }

                    }
                }
            }

        }

    }
}
