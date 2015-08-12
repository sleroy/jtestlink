/*
 * Corolla - A Tool to manage software requirements and test cases 
 * Copyright (C) 2015 Tocea
 * 
 * This file is part of Corolla.
 * 
 * Corolla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, 
 * or any later version.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.requirements.trees.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class RequirementTreeNodeNotFoundException extends CorollaDomainException {

	private static final String MESSAGE = "Requirement tree node not found";
	
	public RequirementTreeNodeNotFoundException() {
		super(MESSAGE);
	}
	
	public RequirementTreeNodeNotFoundException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
}
