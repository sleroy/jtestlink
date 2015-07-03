/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class MissingRoleInformationException extends CorollaDomainException {

	/**
	 * @param _message
	 */
	public MissingRoleInformationException(final String _message) {
		super(_message);
	}

}
