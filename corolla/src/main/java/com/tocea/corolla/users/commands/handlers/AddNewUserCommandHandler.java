/**
 *
 */
package com.tocea.corolla.users.commands.handlers;

import java.util.Date;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.AddNewUserCommand;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.User;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class AddNewUserCommandHandler implements
ICommandHandler<AddNewUserCommand, User>{

	@Autowired
	private IUserDAO userDAO;

	/* (non-Javadoc)
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang.Object)
	 */
	@Override
	public User handle(final AddNewUserCommand _command) {
		final User user = _command.getUser();
		user.setActive(false);
		user.setCreatedTime(new Date());
		user.setLocale(Locale.getDefault().toString());
		this.userDAO.save(user);
		return user;
	}

}
