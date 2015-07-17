package com.tocea.corolla.portfolio.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectNodeAlreadyExistException extends CorollaDomainException {

	private static final String MESSAGE = "This project node already exists";
	
	public ProjectNodeAlreadyExistException() {
		super(MESSAGE);
	}
	
	public ProjectNodeAlreadyExistException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
