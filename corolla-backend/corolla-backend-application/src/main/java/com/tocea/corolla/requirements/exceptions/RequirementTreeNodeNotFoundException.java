package com.tocea.corolla.requirements.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class RequirementTreeNodeNotFoundException extends CorollaDomainException {

	private static String MESSAGE = "Requirement tree node not found";
	
	public RequirementTreeNodeNotFoundException() {
		super(MESSAGE);
	}
	
	public RequirementTreeNodeNotFoundException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
