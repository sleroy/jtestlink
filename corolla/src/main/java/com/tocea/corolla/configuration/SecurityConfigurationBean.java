/**
 *
 */
package com.tocea.corolla.configuration;

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

	private int passwordStrength;

	/**
	 * @return the passwordStrength
	 */
	public int getPasswordStrength() {
		return this.passwordStrength;
	}

	/**
	 * @param _passwordStrength the passwordStrength to set
	 */
	public void setPasswordStrength(final int _passwordStrength) {
		this.passwordStrength = _passwordStrength;
	}

}
