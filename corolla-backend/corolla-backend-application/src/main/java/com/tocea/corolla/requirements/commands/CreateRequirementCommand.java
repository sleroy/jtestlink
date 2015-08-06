package com.tocea.corolla.requirements.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.requirements.domain.Requirement;

@CommandOptions
public class CreateRequirementCommand {

	@NotNull
	private Requirement requirement;
	
	private Integer parentNodeID;
	
	public CreateRequirementCommand() {
		super();
	}

	public CreateRequirementCommand(Requirement req) {
		super();
		setRequirement(req);
	}
	
	public CreateRequirementCommand(Requirement req, Integer parentNodeID) {
		super();
		setRequirement(req);
		setParentNodeID(parentNodeID);
	}
	
	public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	public Integer getParentNodeID() {
		return parentNodeID;
	}

	public void setParentNodeID(Integer parentNodeID) {
		this.parentNodeID = parentNodeID;
	}
	
}
