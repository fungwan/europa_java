package com.erathink.europa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.erathink.europa")
public class EuropaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EuropaApplication.class, args);
	}
}
