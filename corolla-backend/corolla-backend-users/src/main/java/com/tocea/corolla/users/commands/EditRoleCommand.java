/**
 *
 */
package com.tocea.corolla.users.commands;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.dto.RoleDTO;

/**
 * @author sleroy
 *
 */
@CommandOptions
public class EditRoleCommand {
	@NotNull
	private Role	role;

	public EditRoleCommand() {
		
	}
	
	/**
	 * @param _role
	 */
	public EditRoleCommand(final Role _role) {
		super();
		this.role = _role;
	}
	
	public EditRoleCommand(final Role _role, final RoleDTO _roleDTO) {
		
		super();
		this.role = _role;
		this.role.setName(_roleDTO.getName());
		this.role.setNote(_roleDTO.getNote());
		
		List<String> permissions = _roleDTO.getPermissions();
		if (permissions != null) {
			this.role.setPermissions(StringUtils.join(permissions, ", "));
		}
		
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return this.role;
	}

	/**
	 * @param _role
	 *            the role to set
	 */
	public void setRole(final Role _role) {
		this.role = _role;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EditRoleCommand [role=" + this.role + "]";
	}

}
