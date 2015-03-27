/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.DomainException;

/**
 * @author sleroy
 *
 */
public class MissingUserInformationException extends DomainException {

	/**
	 * @param _message
	 */
	public MissingUserInformationException(final String _message) {
		super(_message);
	}

}
