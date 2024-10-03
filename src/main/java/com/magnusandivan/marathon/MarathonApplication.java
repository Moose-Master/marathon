package com.magnusandivan.marathon;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import com.magnusandivan.marathon.api.ImplementationSet;
import com.magnusandivan.marathon.changable_implementations.BasicDatabase;
import com.magnusandivan.marathon.changable_implementations.BasicImplementationSet;
import com.magnusandivan.marathon.changable_implementations.Message;

@Controller
@SpringBootApplication
public class MarathonApplication {
	public static Database Database;
	/**
	 * You should change this when you want to change the behavior being used for
	 * different things
	 */
	public static ImplementationSet ImplementationSet = new BasicImplementationSet();

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MarathonApplication.class, args);
		System.out.println("Hello, world!");
		Database = ImplementationSet.newDatabase();
		// Insert a random chat message for example usage of the database
		UUID chatId = BasicDatabase.GlobalChatId;
		Database.insertMessages(chatId, new Message[] {
				new Message("Hello!", Instant.now(), UUID.randomUUID())
		});
	}
}
