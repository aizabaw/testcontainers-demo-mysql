package com.testcontainers.demo;

import org.springframework.boot.SpringApplication;

public class TestTestcontainersDemoMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.from(TestcontainersDemoMysqlApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
