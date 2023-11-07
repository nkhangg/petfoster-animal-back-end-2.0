package com.poly.petfoster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class PetfosterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetfosterApplication.class, args);
	}

}
