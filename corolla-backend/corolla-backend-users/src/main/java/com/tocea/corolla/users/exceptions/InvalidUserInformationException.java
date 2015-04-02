/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.DomainException;

/**
 * @author sleroy
 *
 */
public class InvalidUserInformationException extends DomainException {

	/**
	 * @param _message
	 */
	public InvalidUserInformationException(final String _message) {
		super(_message);
	}


}
