package com.tocea.corolla.requirements.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class RequirementNotFoundException extends CorollaDomainException {

	private static String MESSAGE = "Requirement not found";
	
	public RequirementNotFoundException() {
		super(MESSAGE);
	}
	
	public RequirementNotFoundException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
