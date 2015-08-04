package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectCategoryAlreadyExistException extends CorollaDomainException {

	private static final String MESSAGE = "This project category already exists.";
	
	public ProjectCategoryAlreadyExistException() {
		super(MESSAGE);
	}
	
}
