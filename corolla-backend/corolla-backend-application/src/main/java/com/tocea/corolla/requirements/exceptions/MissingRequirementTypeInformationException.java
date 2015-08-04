package com.tocea.corolla.requirements.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class MissingRequirementTypeInformationException extends CorollaDomainException {

	public MissingRequirementTypeInformationException(String message) {
		super(message);
	}
	
}
