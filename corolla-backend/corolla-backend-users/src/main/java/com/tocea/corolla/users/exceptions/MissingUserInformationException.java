/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class MissingUserInformationException extends CorollaDomainException {

	/**
	 * @param _message
	 */
	public MissingUserInformationException(final String _message) {
		super(_message);
	}

}
