/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.DomainException;

/**
 * @author sleroy
 *
 */
public class InvalidRoleInformationException extends DomainException {

	/**
	 * @param _message
	 */
	public InvalidRoleInformationException(final String _message) {
		super(_message);
	}


}
