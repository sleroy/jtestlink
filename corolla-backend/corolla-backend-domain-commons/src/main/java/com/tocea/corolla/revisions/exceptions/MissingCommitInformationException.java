package com.tocea.corolla.revisions.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingCommitInformationException extends CorollaDomainException {

	public MissingCommitInformationException(String message) {
		super(message);
	}
	
}
