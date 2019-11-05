package cn.com.sdcsoft.iotapi.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfiguration {

    @Bean
    public  OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.MINUTES);
        builder.readTimeout(20, TimeUnit.SECONDS);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("39.96.216.30", 3128));
        builder.proxy(proxy);
        CookieManager cookieManager = new CookieManager();
        OkHttpClient client = builder
                .build();
        return client;
    }
}
