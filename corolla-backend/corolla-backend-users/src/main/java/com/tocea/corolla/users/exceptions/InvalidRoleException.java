/**
 *
 */
package com.tocea.corolla.users.exceptions;

import javax.validation.ValidationException;

/**
 * @author sleroy
 *
 */
public class InvalidRoleException extends ValidationException {

	private static final String	THE_LOGIN_IS_INVALID	= "This role does not exist.";

	public InvalidRoleException() {
		super(THE_LOGIN_IS_INVALID);
	}

	public InvalidRoleException(final Throwable _cause) {
		super(THE_LOGIN_IS_INVALID, _cause);

	}

}
