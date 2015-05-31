/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class InvalidRoleInformationException extends CorollaDomainException {

	/**
	 * @param _message
	 */
	public InvalidRoleInformationException(final String _message) {
		super(_message);
	}


}
