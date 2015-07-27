package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class InvalidProjectBranchInformationException extends CorollaDomainException {

	public InvalidProjectBranchInformationException(String message) {
		super(message);
	}
	
}
