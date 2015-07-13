package com.tocea.corolla.requirements.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class RequirementTreeNodeAlreadyExistException extends CorollaDomainException {
	
	private static String MESSAGE = "This requirement tree node already exists";
	
	public RequirementTreeNodeAlreadyExistException() {
		super(MESSAGE);
	}
	
	public RequirementTreeNodeAlreadyExistException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}