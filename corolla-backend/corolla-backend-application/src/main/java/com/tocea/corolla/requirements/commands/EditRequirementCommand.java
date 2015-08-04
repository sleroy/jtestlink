package com.tocea.corolla.requirements.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.requirements.domain.Requirement;

@Command
public class EditRequirementCommand {

	@NotNull
	private Requirement requirement;
	
	public EditRequirementCommand() {
		super();
	}
	
	public EditRequirementCommand(Requirement requirement) {
		super();
		setRequirement(requirement);
	}

	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
	
}
