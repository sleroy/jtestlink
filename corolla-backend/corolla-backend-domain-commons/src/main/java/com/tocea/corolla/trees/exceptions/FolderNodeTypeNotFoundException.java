package com.tocea.corolla.trees.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class FolderNodeTypeNotFoundException extends CorollaDomainException {

	public FolderNodeTypeNotFoundException(String typeID) {
		super("No folder node type found for the ID "+typeID);
	}
	
}
