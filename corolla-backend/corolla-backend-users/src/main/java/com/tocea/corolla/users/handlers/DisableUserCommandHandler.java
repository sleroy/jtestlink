/**
 *
 */
package com.tocea.corolla.users.handlers;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.DisableUserCommand;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.User;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class DisableUserCommandHandler implements
ICommandHandler<DisableUserCommand, Boolean> {

	private static final Logger LOGGER = LoggerFactory.getLogger(DisableUserCommandHandler.class);

	@Autowired
	private IUserDAO				userDAO;


	/* (non-Javadoc)
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang.Object)
	 */
	@Override
	public Boolean handle(final DisableUserCommand _command) {

		final User user = this.userDAO.findUserByLogin(_command.getUserLogin());
		final Boolean found =  user != null;
		if (found) {
			LOGGER.info("Disable user {}", user.getLogin());
			user.setActive(false);
			this.userDAO.save(user);

		}

		return found;
	}

}
