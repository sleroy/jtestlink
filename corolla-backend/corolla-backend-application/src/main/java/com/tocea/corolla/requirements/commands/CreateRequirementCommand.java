package com.tocea.corolla.requirements.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.requirements.domain.Requirement;

@Command
public class CreateRequirementCommand {

	@NotNull
	private Requirement requirement;
	
	public CreateRequirementCommand() {
		super();
	}

	public CreateRequirementCommand(Requirement req) {
		super();
		setRequirement(req);
	}
	
	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}
	
}
