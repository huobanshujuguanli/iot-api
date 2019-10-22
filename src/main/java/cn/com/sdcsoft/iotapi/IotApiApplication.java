package cn.com.sdcsoft.iotapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("cn.com.sdcsoft.iotapi.*")
@SpringBootApplication
public class IotApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotApiApplication.class, args);
	}

}
