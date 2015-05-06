/**
 *
 */
package com.tocea.corolla.users.exceptions;

import javax.validation.ValidationException;

/**
 * @author sleroy
 *
 */
public class OperationForbidenWithThisLoginException extends
ValidationException {

	private static final String	THE_LOGIN_IS_INVALID	= "Operation forbidden with this login";

	public OperationForbidenWithThisLoginException() {
		super(THE_LOGIN_IS_INVALID);
	}

	public OperationForbidenWithThisLoginException(final Throwable _cause) {
		super(THE_LOGIN_IS_INVALID, _cause);

	}

}
