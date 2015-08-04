package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingProjectStatusInformationException extends CorollaDomainException {

	public MissingProjectStatusInformationException(String msg) {
		super(msg);
	}
	
}
