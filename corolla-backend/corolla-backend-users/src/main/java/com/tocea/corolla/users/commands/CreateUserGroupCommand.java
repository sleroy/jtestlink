package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.users.domain.UserGroup;

@Command
public class CreateUserGroupCommand {

	@NotNull
	private UserGroup userGroup;
	
	public CreateUserGroupCommand() {
		super();
	}
	
	public CreateUserGroupCommand(UserGroup _group) {
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
