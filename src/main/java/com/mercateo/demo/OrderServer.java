package com.mercateo.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.mercateo.demo")
public class OrderServer extends SpringBootServletInitializer {

	@Value("${server.port}")
	private String serverPort;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrderServer.class, args);
	}
}
