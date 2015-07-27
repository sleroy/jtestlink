package com.tocea.corolla.trees.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingTreeInformationException extends CorollaDomainException {

	public MissingTreeInformationException(String msg) {
		super(msg);
	}
	
}
