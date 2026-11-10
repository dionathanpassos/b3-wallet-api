package com.github.dionathanpassos.b3_wallet_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class B3WalletApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(B3WalletApiApplication.class, args);
	}

}
