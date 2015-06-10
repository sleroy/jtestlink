package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class RoleOperationForbiddenException extends CorollaDomainException {

	private static final String MESSAGE = "Operation forbidden on this role";
	
	public RoleOperationForbiddenException(String _message) {
		super(MESSAGE + ": " + _message);
	}
	
}
