package com.zrd.eeuapigateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class EeuApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EeuApiGatewayApplication.class, args);
    }

}
