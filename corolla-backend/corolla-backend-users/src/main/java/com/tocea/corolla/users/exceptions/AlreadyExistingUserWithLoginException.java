/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class AlreadyExistingUserWithLoginException extends
		CorollaDomainException {
	public AlreadyExistingUserWithLoginException(final String _message) {
		super("User with this login is already existing   "+ _message);

	}

}
