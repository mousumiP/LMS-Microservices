package com.lms.serviceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LmsServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsServiceRegistryApplication.class, args);
	}

}
