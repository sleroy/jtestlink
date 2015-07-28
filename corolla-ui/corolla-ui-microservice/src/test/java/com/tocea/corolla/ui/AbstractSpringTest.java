package com.tocea.corolla.ui;

import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.tocea.corolla.CorollaGuiApplication;

@ActiveProfiles({ "test" })
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { CorollaGuiApplication.class })
@WebAppConfiguration
@IntegrationTest({"server.port=0", "management.port=0"})
public abstract class AbstractSpringTest {

	@Profile("test")
	@Configuration
	@EnableMongoRepositories(basePackages = { "com.tocea.corolla" })
	static class MongoConfiguration extends AbstractMongoConfiguration {

		@Override
		protected String getDatabaseName() {
			return "corolla-test";
		}

		@Bean
		public Mongo mongo() throws Exception {
			return new Fongo("corolla-test").getMongo();
		}

	}
	
}
