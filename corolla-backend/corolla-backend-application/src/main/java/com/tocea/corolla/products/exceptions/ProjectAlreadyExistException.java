package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectAlreadyExistException extends CorollaDomainException {

	final static String MESSAGE = "This project already exists";
	
	public ProjectAlreadyExistException() {
		super(MESSAGE);
	}
	
	public ProjectAlreadyExistException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
