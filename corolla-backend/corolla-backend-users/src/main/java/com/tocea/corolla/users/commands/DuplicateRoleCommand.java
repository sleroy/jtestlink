/**
 *
 */
package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;

/**
 * @author sleroy
 *
 */
@CommandOptions
public class DuplicateRoleCommand {
	@NotNull
	private String	roleID;

	public DuplicateRoleCommand() {
		super();
	}

	public DuplicateRoleCommand(final String _roleID) {
		this.roleID = _roleID;

	}

	/**
	 * @return the roleID
	 */
	public String getRoleID() {
		return this.roleID;
	}

	/**
	 * @param _roleName
	 *            the roleID to set
	 */
	public void setRoleID(final String _roleID) {
		this.roleID = _roleID;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DuplicateRoleCommand [roleID=" + this.roleID + "]";
	}

}
