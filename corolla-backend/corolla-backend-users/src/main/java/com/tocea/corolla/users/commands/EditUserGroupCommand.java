package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.users.domain.UserGroup;

@CommandOptions
public class EditUserGroupCommand {

	@NotNull
	private UserGroup userGroup;
	
	public EditUserGroupCommand() {
		super();
	}
	
	public EditUserGroupCommand(UserGroup _group) {
		super();
		userGroup = _group;
	}

	public UserGroup getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}
	
}
