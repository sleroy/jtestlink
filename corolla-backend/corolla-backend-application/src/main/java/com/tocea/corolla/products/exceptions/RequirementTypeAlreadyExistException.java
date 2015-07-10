package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class RequirementTypeAlreadyExistException extends CorollaDomainException {

	private static String MESSAGE = "This requirement type already exists";
	
	public RequirementTypeAlreadyExistException() {
		super(MESSAGE);
	}
	
	public RequirementTypeAlreadyExistException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
