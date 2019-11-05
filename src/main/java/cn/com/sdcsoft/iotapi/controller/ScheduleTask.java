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
    //1800000 三十分钟
    @Scheduled(fixedDelay=300000)
    public void check() throws IOException {
        List<Sim> simList=  simMapper.findSim(new Sim());
        for(int i=0;i<simList.size();i++){
            String updateSimDate="";
            String insertDate="";
            Request cookieRequest = new Request.Builder()
                    .url("https://api.iot.10086.cn/v2/querycardlifecycle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000432&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+simList.get(i).getSimNo())
                    .get()
                    .build();
            Response execute = okHttpClient.newCall(cookieRequest).execute();
            JSONObject lifecycleResult = JSONObject.parseObject(execute.body().string());
            execute.close();

            if(lifecycleResult.get("status").equals("0")){
                JSONArray lifecycleArray = JSONObject.parseArray(lifecycleResult.get("result").toString());
                if(!lifecycleArray.getJSONObject(0).get("lifecycle").equals(simList.get(i).getLifecycle())){
                    updateSimDate="update_"+simList.get(i).getId()+"_Lifecycle_"+lifecycleArray.getJSONObject(0).get("lifecycle");
                    insertDate="insert_"+simList.get(i).getSimNo()+"_"+simList.get(i).getLifecycle()+"_"+lifecycleArray.getJSONObject(0).get("lifecycle")+"_1";
                    //记录 生命周期状态变更
                    rabbitTemplate.convertAndSend(insertDate);

                    cookieRequest = new Request.Builder()
                            .url("https://api.iot.10086.cn/v2/gprsrealsingle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000008&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+simList.get(i).getSimNo())
                            .get()
                            .build();
                    execute = okHttpClient.newCall(cookieRequest).execute();
                    JSONObject onloneResult = JSONObject.parseObject(execute.body().string());
                    if(onloneResult.get("status").equals("0")){
                        JSONArray onloneArray = JSONObject.parseArray(onloneResult.get("result").toString());

                        if(!onloneArray.getJSONObject(0).get("GPRSSTATUS").equals(simList.get(i).getState())){
                            if(onloneArray.getJSONObject(0).get("GPRSSTATUS").equals("")){
                                updateSimDate+="_State_00";

                            }else{
                                updateSimDate+="_State_"+onloneArray.getJSONObject(0).get("GPRSSTATUS");
                                insertDate="insert_"+simList.get(i).getSimNo()+"_"+simList.get(i).getState()+"_"+onloneArray.getJSONObject(0).get("GPRSSTATUS")+"_2";
                                //记录 在线状态变更
                                rabbitTemplate.convertAndSend(insertDate);
                            }
                            rabbitTemplate.convertAndSend(updateSimDate);
                        }
                    }

                }
            }

        }

    }
}
