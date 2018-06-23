package com.codebase.codechallenge.kalagame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class KalaGameRESTApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalaGameRESTApplication.class, args);
	}
}
