package com.tocea.corolla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableJpaRepositories
@EnableAutoConfiguration
public class CorollaProgramTestApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CorollaProgramTestApplication.class, args);
	}
}
