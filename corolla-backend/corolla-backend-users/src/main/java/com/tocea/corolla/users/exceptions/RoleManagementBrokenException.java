/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.DomainException;

/**
 * @author sleroy
 *
 */
public class RoleManagementBrokenException extends DomainException {
	/**
	 * @param _message
	 */
	public RoleManagementBrokenException(final String _message) {
		super(_message);
	}

}
