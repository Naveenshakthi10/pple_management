package com.example.peoplemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@SpringBootApplication
@EntityScan
@EnableJpaRepositories
@OpenAPIDefinition(info = @Info(title = "People Management", version = "1.0", description = "People Management"))
public class PeoplemanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeoplemanagementApplication.class, args);
	}

}
