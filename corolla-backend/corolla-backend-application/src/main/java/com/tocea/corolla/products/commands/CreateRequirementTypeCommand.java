package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.RequirementType;

@Command
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
