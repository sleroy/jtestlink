package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class InvalidProjectInformationException extends CorollaDomainException {

	public InvalidProjectInformationException() {
		super();
	}
	
	public InvalidProjectInformationException(String message) {
		super(message);
	}
	
}
