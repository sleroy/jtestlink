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
public class DeleteRoleCommand {
	@NotNull
	private String	roleID;

	public DeleteRoleCommand() {
		super();
	}

	public DeleteRoleCommand(final String id) {
		this.roleID = id;

	}

	public String getRoleID() {
		return this.roleID;
	}

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
		return "DeleteRoleCommand [roleID=" + this.roleID + "]";
	}

}
