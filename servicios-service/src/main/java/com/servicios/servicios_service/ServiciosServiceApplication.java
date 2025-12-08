package com.servicios.servicios_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.servicios.servicios_service.client")
public class ServiciosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiciosServiceApplication.class, args);
	}

}
