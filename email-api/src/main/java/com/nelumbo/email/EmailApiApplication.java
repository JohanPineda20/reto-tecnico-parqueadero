package com.nelumbo.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class EmailApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailApiApplication.class, args);
	}

}
