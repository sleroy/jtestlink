package com.tocea.corolla.requirements.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class RequirementAlreadyExistException extends CorollaDomainException {

	private static String MESSAGE = "This requirement already exists";
	
	public RequirementAlreadyExistException() {
		super(MESSAGE);
	}
	
	public RequirementAlreadyExistException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
