/**
 *
 */
package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

/**
 * @author sleroy
 *
 */
public class RoleManagementBrokenException extends CorollaDomainException {
	/**
	 * @param _message
	 */
	public RoleManagementBrokenException(final String _message) {
		super(_message);
	}

}
