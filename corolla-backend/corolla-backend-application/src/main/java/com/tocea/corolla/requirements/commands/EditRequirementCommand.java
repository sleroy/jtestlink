package com.tocea.corolla.requirements.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.requirements.domain.Requirement;

@CommandOptions
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
