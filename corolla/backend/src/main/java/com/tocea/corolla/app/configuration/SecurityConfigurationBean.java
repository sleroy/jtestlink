/**
 *
 */
package com.tocea.corolla.app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * This class defines the security configuration for Corolla;
 *
 * @author sleroy
 *
 */
@Component
@ConfigurationProperties(prefix = "corolla.security")
public class SecurityConfigurationBean {

	private String internalRememberToken;

	private int passwordStrength;



	/**
	 * @return the internalRememberToken
	 */
	public String getInternalRememberToken() {
		return this.internalRememberToken;
	}

	/**
	 * @return the passwordStrength
	 */
	public int getPasswordStrength() {
		return this.passwordStrength;
	}

	/**
	 * @param _internalRememberToken the internalRememberToken to set
	 */
	public void setInternalRememberToken(final String _internalRememberToken) {
		this.internalRememberToken = _internalRememberToken;
	}

	/**
	 * @param _passwordStrength the passwordStrength to set
	 */
	public void setPasswordStrength(final int _passwordStrength) {
		this.passwordStrength = _passwordStrength;
	}

}
