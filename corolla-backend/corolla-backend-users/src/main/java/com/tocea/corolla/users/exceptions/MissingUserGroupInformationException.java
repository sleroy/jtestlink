package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingUserGroupInformationException extends CorollaDomainException {

	public MissingUserGroupInformationException(final String _message) {
		super(_message);
	}
	
}
