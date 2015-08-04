package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingProjectBranchInformationException extends CorollaDomainException {

	public MissingProjectBranchInformationException(String message) {
		super(message);
	}
	
}
