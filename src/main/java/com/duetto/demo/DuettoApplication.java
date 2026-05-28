package com.duetto.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DuettoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DuettoApplication.class, args);
	}

}
