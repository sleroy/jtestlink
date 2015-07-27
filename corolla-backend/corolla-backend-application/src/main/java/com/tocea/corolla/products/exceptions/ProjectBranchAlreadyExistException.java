package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectBranchAlreadyExistException extends CorollaDomainException {

	private static String MESSAGE = "This project branch already exists";
	
	public ProjectBranchAlreadyExistException() {
		super(MESSAGE);
	}
	
	public ProjectBranchAlreadyExistException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
