package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class ProjectBranchOperationForbiddenException extends CorollaDomainException {

	public ProjectBranchOperationForbiddenException(String message) {
		super(message);
	}
	
}
