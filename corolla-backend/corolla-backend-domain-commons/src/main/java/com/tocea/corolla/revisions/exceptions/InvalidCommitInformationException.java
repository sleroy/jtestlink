package com.tocea.corolla.revisions.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class InvalidCommitInformationException extends CorollaDomainException {

	public InvalidCommitInformationException(String message) {
		super(message);
	}
	
}
