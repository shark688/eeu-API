package com.zrd.eeuapisdk;

import com.zrd.eeuapisdk.client.EeuClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("zrd.client")
@Data
@ComponentScan
public class EeuAPIClientConfig {
    private String accessKey;

    private String secretKey;

    @Bean
    public EeuClient eeuClient(){
        return new EeuClient(accessKey,secretKey);
    }
}
