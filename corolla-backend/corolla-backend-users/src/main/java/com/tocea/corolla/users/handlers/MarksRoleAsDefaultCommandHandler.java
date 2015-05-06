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
import com.tocea.corolla.users.service.RolePermissionService;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class MarksRoleAsDefaultCommandHandler implements
ICommandHandler<MarksRoleAsDefaultCommand, Boolean> {

	@Autowired
	private IRoleDAO				roleDAO;

	@Autowired
	private RolePermissionService rolePermissionService;


	@Override
	public Boolean handle(@Valid final MarksRoleAsDefaultCommand _command) {
		final Integer roleID = _command.getRoleID();
		boolean found = false;
		for (final Role role : this.roleDAO.findAll()) {
			final boolean active = roleID.equals(role.getId());
			found |= active;
			role.setDefaultRole(active);
			this.roleDAO.save(role);
		}

		return found;
	}


}
