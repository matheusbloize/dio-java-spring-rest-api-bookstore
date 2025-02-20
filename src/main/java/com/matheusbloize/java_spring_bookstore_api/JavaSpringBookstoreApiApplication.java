package com.matheusbloize.java_spring_bookstore_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(servers = { @Server(url = "/", description = "Default Server URL") })
@SpringBootApplication
public class JavaSpringBookstoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringBookstoreApiApplication.class, args);
	}

}
