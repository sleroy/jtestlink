package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class InvalidProjectCategoryInformationException extends CorollaDomainException {

	public InvalidProjectCategoryInformationException(String msg) {
		super(msg);
	}
	
}
