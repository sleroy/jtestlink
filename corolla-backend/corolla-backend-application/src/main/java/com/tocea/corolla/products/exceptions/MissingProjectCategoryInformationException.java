package com.tocea.corolla.products.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingProjectCategoryInformationException extends CorollaDomainException {

	public MissingProjectCategoryInformationException(String msg) {
		super(msg);
	}
	
}
