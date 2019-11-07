package cn.com.sdcsoft.iotapi.config;

import com.rabbitmq.client.impl.CredentialsProvider;
import okhttp3.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfiguration {

    @Bean
    public  OkHttpClient okHttpClient() {
//
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("39.96.216.30", 3125));//设置socks代理服务器ip端口
//        java.net.Authenticator.setDefault(new java.net.Authenticator()//由于okhttp好像没有提供socks设置Authenticator用户名密码接口，因此设置一个全局的Authenticator
//        {
//            private PasswordAuthentication authentication = new PasswordAuthentication("80201288@qq.com", "Test@163.com".toCharArray());
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication()
//            {
//                return authentication;
//            }
//        });
//        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).proxy(proxy).build();//创建OkHttpClient，并且设置超时时间和代理

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("39.96.216.30", 3125));

        Authenticator proxyAuthenticator = new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic("80201288@qq.com", "Test@163.com");
                return response.request().newBuilder()
                        .header("Proxy-Authorization", credential)
                        .build();
            }
        };
        OkHttpClient client = new OkHttpClient().newBuilder().
                connectTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS).proxy(proxy)
                .proxyAuthenticator(proxyAuthenticator)
                // 解决内存溢出问题
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS)).build();
        return client;
    }
}
