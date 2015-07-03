package com.tocea.corolla.users.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.DeleteUserGroupCommand;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.domain.UserGroup;
import com.tocea.corolla.users.exceptions.InvalidUserGroupInformationException;

@CommandHandler
@Transactional
public class DeleteUserGroupCommandHandler implements ICommandHandler<DeleteUserGroupCommand, UserGroup> {

	@Autowired
	private IUserGroupDAO groupDAO;
	
	@Override
	public UserGroup handle(@Valid DeleteUserGroupCommand command) {
		
		String id = command.getGroupID();
		
		if (id == null || id.equals("")) {
			throw new InvalidUserGroupInformationException("No ID found");
		}
		
		UserGroup group = groupDAO.findOne(id);
		
		if (group == null) {
			throw new InvalidUserGroupInformationException("this user group does not exist");
		}
		
		groupDAO.delete(group);
		
		return group;

	}
	
	
}
