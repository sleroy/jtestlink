package com.tocea.corolla.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tocea.corolla.app.configuration.SecurityConfigurationBean;
import com.tocea.corolla.users.api.IPasswordEncoder;

/**
 * This service defines the way to encode passwords.
 */
@Service("passwordEncoder")
public class PasswordEncoder implements IPasswordEncoder {


	@Autowired
	private SecurityConfigurationBean	securityConfigurationBean;

	public PasswordEncoder() {

		super();
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.users.api.IPasswordEncoder#encodePassword(java.lang.String)
	 */
	@Override
	public String encodePassword(final String _password) {
		final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(this.securityConfigurationBean.getPasswordStrength());
		return bCryptPasswordEncoder.encode(_password);
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.users.api.IPasswordEncoder#isPasswordMatching(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isPasswordMatching(final String _rawPassword, final String _encodedPassword) {
		final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(this.securityConfigurationBean.getPasswordStrength());
		return bCryptPasswordEncoder.matches(_rawPassword, _encodedPassword);
	}


}