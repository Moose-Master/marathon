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

import com.magnusandivan.marathon.api.Database;
import com.magnusandivan.marathon.api.ImplementationSet;
import com.magnusandivan.marathon.changable_implementations.BasicDatabase;
import com.magnusandivan.marathon.changable_implementations.BasicImplementationSet;
import com.magnusandivan.marathon.changable_implementations.Message;

@Controller
@SpringBootApplication
public class MarathonApplication {
	/**
	 * You should change this when you want to change the behavior being used for
	 * different things
	 */
	public static ImplementationSet ImplementationSet = new BasicImplementationSet();
	public static Database Database = ImplementationSet.newDatabase();

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MarathonApplication.class, args);
		System.out.println("Hello, world!");
		// Insert a random chat message for example usage of the database
		UUID chatId = BasicDatabase.GlobalChatId;
		// This is necessary to load the Database class and initialize the messages
		Database.insertMessages(chatId, new Message[] {});
	}
}
