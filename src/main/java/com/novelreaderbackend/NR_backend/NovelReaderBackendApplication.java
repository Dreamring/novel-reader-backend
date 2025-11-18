package com.novelreaderbackend.NR_backend;

import com.novelreaderbackend.utils.InitializationChecker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NovelReaderBackendApplication {

	public static void main(String[] args) {
		InitializationChecker.check();
		SpringApplication.run(NovelReaderBackendApplication.class, args);
	}

}