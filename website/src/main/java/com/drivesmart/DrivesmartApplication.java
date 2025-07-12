package com.drivesmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class DrivesmartApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrivesmartApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void printStartupMessage() {
		System.out.println("\n========================================");
		System.out.println("Website disponível em: http://localhost:8081/");
		System.out.println("========================================\n");
	}
}
