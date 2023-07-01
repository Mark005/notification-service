package com.bmo.common.notification_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
		try {
			//new Test();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
