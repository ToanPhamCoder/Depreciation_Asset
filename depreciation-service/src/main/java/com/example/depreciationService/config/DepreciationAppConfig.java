package com.example.depreciationService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
//@EnableScheduling
public class DepreciationAppConfig {
    @Bean
    @LoadBalanced
    public RestTemplate template(){
        return new RestTemplate();
    }
}
