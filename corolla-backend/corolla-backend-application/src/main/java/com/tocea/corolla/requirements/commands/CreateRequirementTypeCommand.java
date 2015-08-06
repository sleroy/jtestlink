package com.tocea.corolla.requirements.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.requirements.domain.RequirementType;

@CommandOptions
public class CreateRequirementTypeCommand {

	@NotNull
	private RequirementType type;
	
	public CreateRequirementTypeCommand() {
		super();
	}
	
	public CreateRequirementTypeCommand(RequirementType _type) {
		super();
		setType(_type);
	}

	public RequirementType getType() {
		return type;
	}

	public void setType(RequirementType type) {
		this.type = type;
	}
	
}
