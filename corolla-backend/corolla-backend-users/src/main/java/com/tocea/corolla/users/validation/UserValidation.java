/**
 *
 */
package com.tocea.corolla.users.validation;

import org.springframework.stereotype.Service;

/**
 * @author sleroy
 *
 */
@Service
public class UserValidation {

	/**
	 * Checks if the login is valid.
	 *
	 * @param _login
	 *            the login name
	 * @return true if the login is valid.
	 */
	public boolean isValidLogin(final String _login) {
		if (_login.length() > 30) {
			return false;
		}
		for (int i = 0; i < _login.length(); ++i) {
			if (!Character.isDigit(_login.charAt(i))
					&& !Character.isLetter(_login.charAt(i))) {
				return false;
			}
		}
		return true;
	}

}
