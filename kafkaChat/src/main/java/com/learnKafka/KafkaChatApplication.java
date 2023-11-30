package com.learnKafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class KafkaChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaChatApplication.class, args);
	}

}
