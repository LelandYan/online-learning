package com.lelandyan.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.lelandyan.staservice.mapper")
@EnableDiscoveryClient //开启服务发现
@ComponentScan("com.lelandyan")
@EnableScheduling
public class StaApplication {
    public static void main(String[] args) {
        System.out.println("SpringBoot Start…");
        try {
            SpringApplication.run(StaApplication.class, args);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }
}
