/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class InvalidUserInformationException extends CorollaDomainException {

	/**
	 * @param _message
	 */
	public InvalidUserInformationException(final String _message) {
		super(_message);
	}


}
