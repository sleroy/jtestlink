package com.tocea.corolla.requirements.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingRequirementInformationException extends CorollaDomainException {

	public MissingRequirementInformationException(String message) {
		super(message);
	}
	
}
