package com.reury.kabanquadro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.reury.kabanquadro.service.DataLoaderService;

@SpringBootApplication
public class QuadrokabanApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuadrokabanApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner loadData(DataLoaderService dataLoaderService) {
	// 	return args -> {
	// 		if (args.length > 0 && args[0].equals("load")) {
	// 			dataLoaderService.loadSampleData();
	// 		}			
	// };
	// }
}
