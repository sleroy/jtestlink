package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectNotFoundException extends CorollaDomainException {
	
	final static String MESSAGE = "Project Not Found";
	
	public ProjectNotFoundException() {
		super(MESSAGE);
	}
	
	public ProjectNotFoundException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
