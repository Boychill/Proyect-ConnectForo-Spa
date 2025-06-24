package com.example.ForumService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient          // para registrarse en Eureka
@EnableFeignClients             // para usar Feign Client
public class ForumServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceApplication.class, args);
	}

}
