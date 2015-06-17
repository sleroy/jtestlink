/**
 *
 */
package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.dto.RoleDTO;

/**
 * @author sleroy
 *
 */
@Command
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
