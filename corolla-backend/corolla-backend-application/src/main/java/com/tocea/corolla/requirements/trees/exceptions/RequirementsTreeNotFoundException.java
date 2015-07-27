package com.tocea.corolla.requirements.trees.exceptions;

import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.utils.domain.CorollaDomainException;

public class RequirementsTreeNotFoundException extends CorollaDomainException {

	private static String MESSAGE = "Requirements Tree not found";
	
	public RequirementsTreeNotFoundException() {
		super(MESSAGE);
	}
	
	public RequirementsTreeNotFoundException(String msg) {
		super(MESSAGE+": "+msg);
	}
	
	public RequirementsTreeNotFoundException(ProjectBranch branch) {
		super(MESSAGE+" for branch "+branch.getName());
	}
}
