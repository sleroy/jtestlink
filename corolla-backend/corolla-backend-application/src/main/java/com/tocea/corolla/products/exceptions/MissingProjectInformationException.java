package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingProjectInformationException extends CorollaDomainException {

	public MissingProjectInformationException() {
		super();
	}
	
	public MissingProjectInformationException(String message) {
		super(message);
	}
	
}
