package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectStatusAlreadyExistException extends CorollaDomainException {

	private static String MESSAGE = "This project status already exists";
	
	public ProjectStatusAlreadyExistException() {
		super(MESSAGE);
	}
	
	public ProjectStatusAlreadyExistException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
