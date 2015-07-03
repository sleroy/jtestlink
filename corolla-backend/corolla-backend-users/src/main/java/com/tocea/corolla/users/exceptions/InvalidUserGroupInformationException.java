package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class InvalidUserGroupInformationException extends CorollaDomainException {

	public InvalidUserGroupInformationException(final String _message) {
		super(_message);
	}
	
}
