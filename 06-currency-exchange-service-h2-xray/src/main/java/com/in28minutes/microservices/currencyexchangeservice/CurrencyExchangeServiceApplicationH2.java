package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CurrencyExchangeServiceApplicationH2 {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeServiceApplicationH2.class, args);
	}
}
