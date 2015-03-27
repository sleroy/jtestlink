/**
 *
 */
package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.users.domain.Role;

/**
 * @author sleroy
 *
 */
@Command
public class EditRoleCommand {
	@NotNull
	private Role	role;

	/**
	 * @param _role
	 */
	public EditRoleCommand(final Role _role) {
		super();
		this.role = _role;
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
