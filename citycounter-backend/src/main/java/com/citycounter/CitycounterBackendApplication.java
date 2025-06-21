package com.citycounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CitycounterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitycounterBackendApplication.class, args);
	}

}
