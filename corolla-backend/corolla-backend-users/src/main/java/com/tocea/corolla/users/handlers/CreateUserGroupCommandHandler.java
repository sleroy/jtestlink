package com.tocea.corolla.users.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.CreateUserGroupCommand;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.domain.UserGroup;
import com.tocea.corolla.users.exceptions.InvalidUserGroupInformationException;
import com.tocea.corolla.users.exceptions.MissingUserGroupInformationException;
import com.tocea.corolla.users.exceptions.UserGroupAlreadyExistException;

@CommandHandler
@Transactional
public class CreateUserGroupCommandHandler implements
		ICommandHandler<CreateUserGroupCommand, UserGroup> {

	@Autowired
	private IUserGroupDAO groupDAO;

	@Override
	public UserGroup handle(@Valid CreateUserGroupCommand command) {

		UserGroup group = command.getUserGroup();

		if (group == null) {
			throw new MissingUserGroupInformationException(
					"No data provided to create user group");
		}

		if (StringUtils.isNotBlank(group.getId())) {
			throw new InvalidUserGroupInformationException("No ID expected");
		}
		
		if (groupDAO.findByName(group.getName()) != null) {
			throw new UserGroupAlreadyExistException();
		}
		
		group.setId(null);

		groupDAO.save(group);

		return group;

	}

}
