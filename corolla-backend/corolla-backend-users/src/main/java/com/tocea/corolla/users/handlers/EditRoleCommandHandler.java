/**
 *
 */
package com.tocea.corolla.users.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.EditRoleCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.exceptions.InvalidRoleException;
import com.tocea.corolla.users.exceptions.InvalidRoleInformationException;
import com.tocea.corolla.users.exceptions.MissingRoleInformationException;
import com.tocea.corolla.users.exceptions.RoleOperationForbiddenException;
import com.tocea.corolla.users.service.RolePermissionService;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class EditRoleCommandHandler implements
ICommandHandler<EditRoleCommand, Role> {

	@Autowired
	private IRoleDAO				roleDAO;

	@Autowired
	private RolePermissionService rolePermissionService;


	@Override
	public Role handle(@Valid final EditRoleCommand _command) {
		
		final Role role = _command.getRole();
		
		if (role == null) {
			throw new MissingRoleInformationException("No data provided to create role");
		}
		
		if (role.getId() == null) {
			throw new InvalidRoleInformationException("ID is missing");
		}
		
		final Role existingRole = roleDAO.findOne(role.getId());
		
		if (existingRole == null) {
			throw new InvalidRoleException();
		}
		
		if (existingRole.isRoleProtected()) {
			throw new RoleOperationForbiddenException("cannot edit a protected role");
		}

		this.rolePermissionService.checkPermissions(role.getPermissions());
		this.roleDAO.save(role);
		
		return role;
	}


}
