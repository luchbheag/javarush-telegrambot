package com.tutorials.javarushcommunity.javarushtelegrambot.jrtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {
		"com.tutorials.javarushcommunity.javarushtelegrambot.jrtb",
		"org.telegram.telegrambots"
})
public class JavarushTelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavarushTelegramBotApplication.class, args);
	}

}
