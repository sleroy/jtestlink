package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectPermissionNotFoundException extends CorollaDomainException {

	private static String MESSAGE = "Project permission not found";
	
	public ProjectPermissionNotFoundException() {
		super(MESSAGE);
	}
	
}
