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
import com.tocea.corolla.users.commands.DeleteRoleCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.domain.Role;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class DeleteRoleCommandHandler implements
ICommandHandler<DeleteRoleCommand, Boolean> {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(DeleteRoleCommandHandler.class);

	@Autowired
	private IRoleDAO			roleDAO;

	@Override
	public Boolean handle(final DeleteRoleCommand _command) {

		final Role role = this.roleDAO.findRoleByName(_command.getRoleName());
		final Boolean found = role != null;
		if (found) {
			LOGGER.info("Delete role {}", role.getName());
			this.roleDAO.delete(role);

		}

		return found;
	}

}
