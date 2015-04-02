package com.tocea.corolla.users.configuration;
/**
 *
 */


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration of the server.
 *
 * @author sleroy
 *
 */
@Component
@ConfigurationProperties(prefix = "corolla.settings.users")
public class UserConfigurationBean {
	private boolean	localAddressAllowed;

	/**
	 * @return the localaddressAllowed
	 */
	public boolean isLocalAddressAllowed() {
		return this.localAddressAllowed;
	}

	/**
	 * @param _localaddressAllowed
	 *            the localaddressAllowed to set
	 */
	public void setLocalAddressAllowed(final boolean _localaddressAllowed) {
		this.localAddressAllowed = _localaddressAllowed;
	}
}
