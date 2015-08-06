package com.tocea.corolla.users.commands;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.cqrs.annotations.CommandOptions;

@CommandOptions
public class DeleteUserGroupCommand {

	@NotBlank
	private String groupID;
	
	public DeleteUserGroupCommand() {
		super();
	}
	
	public DeleteUserGroupCommand(String _groupID) {
		super();
		setGroupID(_groupID);
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
}
