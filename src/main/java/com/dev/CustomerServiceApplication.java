package com.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement

public class CustomerServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}	

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(10);
	}
	

}
