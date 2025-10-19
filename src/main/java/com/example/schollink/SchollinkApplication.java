package com.example.schollink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchollinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchollinkApplication.class, args);
	}

}
