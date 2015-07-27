package com.tocea.corolla.requirements.commands;

import org.hibernate.validator.constraints.NotEmpty;

import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class DeleteRequirementCommand {

	@NotEmpty
	private String requirementID;
	
	public DeleteRequirementCommand() {
		super();
	}
	
	public DeleteRequirementCommand(String requirementID) {
		super();
		setRequirementID(requirementID);
	}

	public String getRequirementID() {
		return requirementID;
	}

	public void setRequirementID(String requirementID) {
		this.requirementID = requirementID;
	}
	
}
