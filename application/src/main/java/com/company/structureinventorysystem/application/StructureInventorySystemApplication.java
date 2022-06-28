package com.company.structureinventorysystem.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.company.structureinventorysystem")
@EntityScan(basePackages = "com.company.structureinventorysystem.domain")
@EnableJpaRepositories(basePackages = "com.company.structureinventorysystem.infrastructure.repository")
public class StructureInventorySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StructureInventorySystemApplication.class, args);
	}

}
