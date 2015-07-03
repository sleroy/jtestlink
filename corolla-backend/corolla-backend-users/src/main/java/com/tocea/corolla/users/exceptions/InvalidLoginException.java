/**
 *
 */
package com.tocea.corolla.users.exceptions;

import javax.validation.ValidationException;

/**
 * @author sleroy
 *
 */
public class InvalidLoginException extends ValidationException {

	private static final String	THE_LOGIN_IS_INVALID	= "The login is invalid";

	public InvalidLoginException() {
		super(THE_LOGIN_IS_INVALID);
	}

	public InvalidLoginException(final Throwable _cause) {
		super(THE_LOGIN_IS_INVALID, _cause);

	}

}
