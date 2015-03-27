/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.DomainException;

/**
 * @author sleroy
 *
 */
public class RoleAlreadyExistException extends DomainException {

	/**
	 * @param _message
	 */
	public RoleAlreadyExistException(final String _message) {
		super("This role already exists " + _message);
	}


}
