package com.tocea.corolla.users.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class UserGroupAlreadyExistException extends CorollaDomainException {

	public UserGroupAlreadyExistException() {
		super("This user group already exists");
	}
	
}
