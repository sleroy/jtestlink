/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.DomainException;

/**
 * @author sleroy
 *
 */
public class AlreadyExistingUserWithLoginException extends DomainException {
	public AlreadyExistingUserWithLoginException(final String _message) {
		super("User with this login is already existing   "+ _message);

	}

}
