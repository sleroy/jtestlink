/**
 *
 */
package com.tocea.corolla.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tocea.corolla.app.configuration.SecurityConfiguration;

/**
 * @author sleroy
 *
 */
@Configuration
public class PasswordEncodingConfigurationBean {
	@Autowired
	private SecurityConfiguration		security;

	@Bean
	public PasswordEncoder getPasswordEncoderBean() {
		return new BCryptPasswordEncoder(this.security.getPasswordStrength());
	}

}
