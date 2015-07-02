package com.tocea.corolla.users.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.EditUserGroupCommand;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.domain.UserGroup;
import com.tocea.corolla.users.exceptions.InvalidUserGroupInformationException;
import com.tocea.corolla.users.exceptions.MissingUserGroupInformationException;
import com.tocea.corolla.users.exceptions.UserGroupAlreadyExistException;

@CommandHandler
@Transactional
public class EditUserGroupCommandHandler implements
		ICommandHandler<EditUserGroupCommand, UserGroup> {

	@Autowired
	private IUserGroupDAO groupDAO;

	@Override
	public UserGroup handle(@Valid EditUserGroupCommand command) {

		UserGroup group = command.getUserGroup();

		if (group == null) {
			throw new MissingUserGroupInformationException(
					"No data provided to edit an user group");
		}

		if (StringUtils.isBlank(group.getId())) {
			throw new InvalidUserGroupInformationException("No ID found"); 
		}
		
		UserGroup groupWithSameName = groupDAO.findByName(group.getName());
		
		if (groupWithSameName != null && groupWithSameName.getId() != group.getId()) {
			throw new UserGroupAlreadyExistException();
		}
		
		groupDAO.save(group);
		
		return group;

	}

}
