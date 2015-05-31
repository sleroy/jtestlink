/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class RoleAlreadyExistException extends CorollaDomainException {

	/**
	 * @param _message
	 */
	public RoleAlreadyExistException(final String _message) {
		super("This role already exists " + _message);
	}


}
