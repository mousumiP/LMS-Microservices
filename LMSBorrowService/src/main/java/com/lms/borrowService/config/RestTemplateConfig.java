package com.lms.borrowService.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced  // This enables Eureka service name resolution
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
    	return builder
                .setConnectTimeout(Duration.ofSeconds(2)) // Max time to connect
                .setReadTimeout(Duration.ofSeconds(5))   // Max time to wait for data
                .build();
    }
}
