/**
 *
 */
package com.tocea.corolla.users.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tocea.corolla.configuration.CorollaConfigurationBean;

/**
 * This service defines the email validation.
 *
 * @author sleroy
 *
 */
@Service
public class EmailValidationService {

	@Autowired
	private CorollaConfigurationBean	config;

	/**
	 * Validates an email
	 *
	 * @return the validation result.
	 */
	public boolean validateEmail(final String emailName) {
		return EmailValidator.getInstance(false).isValid(emailName);
	}

}
