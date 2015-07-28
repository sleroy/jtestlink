package com.tocea.corolla.trees.commands;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class DeleteFolderNodeTypeCommand {

	@NotBlank
	private String typeID;

	public DeleteFolderNodeTypeCommand() {
		super();
	}
	
	public DeleteFolderNodeTypeCommand(String typeID) {
		super();
		setTypeID(typeID);
	}
	
	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	
}
