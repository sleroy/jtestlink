/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.DomainException;

/**
 * @author sleroy
 *
 */
public class MissingRoleInformationException extends DomainException {

	/**
	 * @param _message
	 */
	public MissingRoleInformationException(final String _message) {
		super(_message);
	}

}
