package com.tocea.corolla

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
class CorollaGuiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CorollaGuiApplication.class, args)
	}
}
