package cn.com.sdcsoft.iotapi.controller;


import cn.com.sdcsoft.iotapi.client.TemplateClient;
import com.alibaba.fastjson.JSON;
import feign.Feign;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/device")
public class DeviceController {
    @Autowired
    private OkHttpClient okHttpClient;

    private static String wxUrl;
    @Value("${com.sdcsoft.datamanage.wx-openid}")
    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl;
    }
    /**
     *
     * @param deviceNo
     * @return
     */
    @GetMapping(value = "/getImei")
    public String getImei(String deviceNo) {
        TemplateClient deviceInfoClient = Feign.builder().target(TemplateClient.class, String.format("%s%s",wxUrl,"/wechat/device/getsuffix"));
        Map<String,String> map=new HashMap<>();
        map.put("deviceNo",deviceNo);
        return deviceInfoClient.get(map);
    }


    /**
     *在线信息实时查询
     * @param iccid
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/gprsrealsingle")
    public Object gprsrealsingle(String iccid) throws IOException {
        Request cookieRequest = new Request.Builder()
                .url("https://api.iot.10086.cn/v2/gprsrealsingle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000008&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+iccid)
                .get()
                .build();
        Response execute = okHttpClient.newCall(cookieRequest).execute();
        Object result=JSON.parse(execute.body().string());
        execute.close();
        return result;
    }
    /**
     *用户状态信息实时查询
     * @param iccid
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/userstatusrealsingle")
    public Object userstatusrealsingle(String iccid) throws IOException {
        Request cookieRequest = new Request.Builder()
                .url("https://api.iot.10086.cn/v2/userstatusrealsingle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000009&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+iccid)
                .get()
                .build();
        Response execute = okHttpClient.newCall(cookieRequest).execute();
        Object result=JSON.parse(execute.body().string());
        execute.close();
        return result;
    }

    /**
     *开关机信息实时查询
     * @param iccid
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/onandoffrealsingle")
    public Object onandoffrealsingle(String iccid) throws IOException {
        Request cookieRequest = new Request.Builder()
                .url("https://api.iot.10086.cn/v2/onandoffrealsingle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000025&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+iccid)
                .get()
                .build();
        Response execute = okHttpClient.newCall(cookieRequest).execute();
        Object result=JSON.parse(execute.body().string());
        execute.close();
        return result;
    }

    /**
     *物联卡生命周期查询
     * @param iccid
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/querycardlifecycle")
    public Object querycardlifecycle(String iccid) throws IOException {
        Request cookieRequest = new Request.Builder()
                .url("https://api.iot.10086.cn/v2/querycardlifecycle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000432&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+iccid)
                .get()
                .build();
        Response execute = okHttpClient.newCall(cookieRequest).execute();
        Object result=JSON.parse(execute.body().string());
        execute.close();
        return result;
    }
    /**
     *物联卡开户日期查询
     * @param iccid
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/querycardopentime")
    public Object querycardopentime (String iccid) throws IOException {
        Request cookieRequest = new Request.Builder()
                .url("https://api.iot.10086.cn/v2/querycardopentime?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000901&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+iccid)
                .get()
                .build();
        Response execute = okHttpClient.newCall(cookieRequest).execute();
        Object result=JSON.parse(execute.body().string());
        execute.close();
        return result;
    }
    /**
     *用户余额信息实时查询
     * @param iccid
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/balancerealsingle")
    public Object balancerealsingle (String iccid) throws IOException {
        Request cookieRequest = new Request.Builder()
                .url("https://api.iot.10086.cn/v2/balancerealsingle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000035&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+iccid)
                .get()
                .build();
        Response execute = okHttpClient.newCall(cookieRequest).execute();
        Object result=JSON.parse(execute.body().string());
        execute.close();
        return result;
    }
    /**
     *用户当月GPRS查询
     * @param iccid
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/gprsusedinfosingle")
    public Object gprsusedinfosingle (String iccid) throws IOException {
        Request cookieRequest = new Request.Builder()
                .url("https://api.iot.10086.cn/v2/gprsusedinfosingle?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000012&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+iccid)
                .get()
                .build();
        Response execute = okHttpClient.newCall(cookieRequest).execute();
        Object result=JSON.parse(execute.body().string());
        execute.close();
        return result;
    }
    /**
     *套餐内GPRS流量使用情况实时查询 (集团客户)
     * @param iccid
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/gprsrealtimeinfo")
    public Object gprsrealtimeinfo (String iccid) throws IOException {
        Request cookieRequest = new Request.Builder()
                .url("https://api.iot.10086.cn/v2/gprsrealtimeinfo?appid=QHSLKYFFDM4TJO&transid=QHSLKYFFDM4TJO2019101410533080000001&ebid=0001000000083&token=3740d7d73ea1e228d08b5045434c09248e0883eb7d5ac0576e69e6fe2f6acd01&iccid="+iccid)
                .get()
                .build();
        Response execute = okHttpClient.newCall(cookieRequest).execute();
        Object result=JSON.parse(execute.body().string());
        execute.close();
        return result;
    }
}
