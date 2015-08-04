package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class InvalidProjectStatusInformationException extends CorollaDomainException {

	public InvalidProjectStatusInformationException(String message) {
		super(message);
	}
	
}
