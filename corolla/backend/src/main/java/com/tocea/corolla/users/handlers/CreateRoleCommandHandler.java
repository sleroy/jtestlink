/**
 *
 */
package com.tocea.corolla.users.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.CreateRoleCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.exceptions.InvalidRoleInformationException;
import com.tocea.corolla.users.exceptions.MissingRoleInformationException;
import com.tocea.corolla.users.exceptions.RoleAlreadyExistException;
import com.tocea.corolla.users.service.RolePermissionService;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class CreateRoleCommandHandler implements
ICommandHandler<CreateRoleCommand, Role> {

	@Autowired
	private IRoleDAO				roleDAO;

	@Autowired
	private RolePermissionService rolePermissionService;


	@Override
	public Role handle(@Valid final CreateRoleCommand _command) {
		final Role role = _command.getRole();
		if (role == null) {
			throw new MissingRoleInformationException("No data provided to create role");
		}
		if (role.getId() != null) {
			throw new InvalidRoleInformationException("No ID expected");
		}
		if (this.roleDAO.findRoleByName(role.getName()) != null) {
			throw new RoleAlreadyExistException(role.getName());
		}
		this.rolePermissionService.checkPermissions(role.getPermissions());
		this.roleDAO.save(role);
		return role;
	}


}
