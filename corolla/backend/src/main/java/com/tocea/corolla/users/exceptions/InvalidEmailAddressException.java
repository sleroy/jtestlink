/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.DomainException;

/**
 * Exception when an email adress is invalid.
 * @author sleroy
 *
 */
public class InvalidEmailAddressException extends DomainException {

	/**
	 * @param _email
	 */
	public InvalidEmailAddressException(final String _email) {
		super("Email address is invalid : " + _email);
	}


}
