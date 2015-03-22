package com.tocea.corolla

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration

@SpringBootApplication
@Configuration
@ComponentScan
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
public class CorollaProgramApplication {

	static void main(final String[] args) {
		SpringApplication.run(CorollaProgramApplication.class, args)
	}
}
