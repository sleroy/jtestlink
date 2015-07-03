/**
 *
 */
package com.tocea.corolla.users.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.users.commands.DuplicateRoleCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.exceptions.MissingRoleInformationException;
import com.tocea.corolla.users.service.RolePermissionService;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class DuplicateRoleCommandHandler implements
ICommandHandler<DuplicateRoleCommand, Role> {

	@Autowired
	private IRoleDAO				roleDAO;

	@Autowired
	private RolePermissionService	rolePermissionService;

	@Override
	public Role handle(@Valid final DuplicateRoleCommand _command) {
		
		final String roleID = _command.getRoleID();
		
		Role roleToDuplicate = this.roleDAO.findOne(roleID.toString());
		
		if (roleToDuplicate == null) {
			throw new MissingRoleInformationException("No role provided");
		}
		
		roleToDuplicate = roleToDuplicate.duplicate();
		roleToDuplicate.setName(roleToDuplicate.getName() + " duplicated");
		roleToDuplicate.setId(null);
		roleToDuplicate.setDefaultRole(false);
		
		this.roleDAO.save(roleToDuplicate);

		return roleToDuplicate;
	}

}
