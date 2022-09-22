package com.Bento.Bento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableJpaRepositories
@EnableMongoRepositories
public class BentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BentoApplication.class, args);
	}

}
