package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectBranchNotFoundException extends CorollaDomainException {

	private static String MESSAGE = "Branch not found";
			
	public ProjectBranchNotFoundException() {
		super(MESSAGE);
	}
	
	public ProjectBranchNotFoundException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
