package com.robcio.imdbNotepad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ImdbNotepadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbNotepadApplication.class, args);
	}
}
