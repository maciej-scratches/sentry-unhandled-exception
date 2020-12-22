package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}

@RestController
class MyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

	@GetMapping("/crash")
	public String triggerException() {
		LOGGER.error("Event from logback");

		throw new RuntimeException(
				"Expected: controller used to showcase what " + "happens when an exception is thrown");
	}
}

@ControllerAdvice
class GlobalErrorHandler {

	@ExceptionHandler
	ResponseEntity<?> handle(RuntimeException e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	}
}
