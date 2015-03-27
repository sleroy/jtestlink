/**
 *
 */
package com.tocea.corolla.users.handlers;

import java.util.Date;
import java.util.Locale;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.AddNewUserCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.exceptions.AlreadyExistingUserWithLoginException;
import com.tocea.corolla.users.exceptions.InvalidEmailAddressException;
import com.tocea.corolla.users.exceptions.InvalidUserInformationException;
import com.tocea.corolla.users.exceptions.MissingUserInformationException;
import com.tocea.corolla.users.exceptions.RoleManagementBrokenException;
import com.tocea.corolla.users.service.EmailValidationService;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class AddNewUserCommandHandler implements
ICommandHandler<AddNewUserCommand, User> {

	@Autowired
	private IUserDAO				userDAO;

	@Autowired
	private IRoleDAO				roleDAO;

	@Autowired
	private EmailValidationService	emailValidationService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang.Object)
	 */
	@Override
	public User handle(@Valid final AddNewUserCommand _command) {
		final User user = _command.getUser();
		if (user == null) {
			throw new MissingUserInformationException("No data provided to create user");
		}
		if (user.getId() != null) {
			throw new InvalidUserInformationException("No ID expected");
		}
		this.checkUserLogin(user);
		user.setActivationToken("");
		user.setActive(false);
		user.setCreatedTime(new Date());
		this.setLocaleIfNecessary(user);
		this.setDefaultRoleIfNecessary(user);
		if (!this.emailValidationService.validateEmail(user.getEmail())) {
			throw new InvalidEmailAddressException(user.getEmail());
		}

		this.userDAO.save(user);
		return user;
	}

	/**
	 * @param _user
	 */
	private void checkUserLogin(final User _user) {
		if (this.userDAO.findUserByLogin(_user.getLogin()) != null) {
			throw new AlreadyExistingUserWithLoginException(_user.getLogin());
		}

	}

	/**
	 * @param user
	 */
	private void setDefaultRoleIfNecessary(final User user) {
		if (user.getRoleId() == null) {
			final Role defaultRole = this.roleDAO.getDefaultRole();
			if (defaultRole == null) {
				throw new RoleManagementBrokenException("Could not find the default role");
			}
			user.setRoleId(defaultRole.getId());

		}
	}

	/**
	 * @param user
	 */
	private void setLocaleIfNecessary(final User user) {
		if (Strings.isNullOrEmpty(user.getLocale())) {
			user.setLocale(Locale.getDefault().toString());
		}
	}

}
