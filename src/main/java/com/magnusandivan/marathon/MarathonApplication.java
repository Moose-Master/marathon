package com.magnusandivan.marathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class MarathonApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarathonApplication.class, args);
		System.out.println("Hello, world!");
	}
}
