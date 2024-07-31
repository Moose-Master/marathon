package com.magnusandivan.marathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarathonApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarathonApplication.class, args);
		System.out.println("Hello, world!");
	}
}
