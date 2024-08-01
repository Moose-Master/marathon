package com.magnusandivan.marathon;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class MarathonApplication {
	public static Database Database;
	public static void main(String[] args) throws IOException {
		SpringApplication.run(MarathonApplication.class, args);
		System.out.println("Hello, world!");
		Database = new BasicDatabase("data");
	}
}
