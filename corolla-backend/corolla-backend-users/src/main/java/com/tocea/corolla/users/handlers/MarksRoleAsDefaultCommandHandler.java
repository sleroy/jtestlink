/**
 *
 */
package com.tocea.corolla.users.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.MarksRoleAsDefaultCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.exceptions.InvalidRoleException;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class MarksRoleAsDefaultCommandHandler implements
ICommandHandler<MarksRoleAsDefaultCommand, Boolean> {

	@Autowired
	private IRoleDAO	roleDAO;

	@Override
	public Boolean handle(@Valid final MarksRoleAsDefaultCommand _command) {
		final Integer roleID = _command.getRoleID();
		if (!this.roleDAO.exists(roleID)) {
			throw new InvalidRoleException("Role was not found " + roleID);
		}
		this.disableOtherRoles();
		this.enableSelectedRole(roleID);

		return true;
	}

	private void disableOtherRoles() {
		for (final Role role : this.roleDAO.findAll()) {
			if (role.isDefaultRole()) {
				role.setDefaultRole(false);
				this.roleDAO.save(role);
			}
		}

	}

	private void enableSelectedRole(final Integer _roleID) {
		final Role role = this.roleDAO.findOne(_roleID);
		role.setDefaultRole();
		this.roleDAO.save(role);
	}

}
