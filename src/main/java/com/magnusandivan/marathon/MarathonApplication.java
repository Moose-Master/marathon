package com.magnusandivan.marathon;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class MarathonApplication {
	public static Database Database;
	public static void main(String[] args) throws IOException {
		SpringApplication.run(MarathonApplication.class, args);
		System.out.println("Hello, world!");
		Database = new BasicDatabase("data");
		// Insert a random chat message for example usage of the database
		UUID chatId = BasicDatabase.GlobalChatId;
		Database.insertMessages(chatId, new Message[] {
			new Message("Hello!", Instant.now(), UUID.randomUUID())
		});
	}
}
