/**
 *
 */
package com.tocea.corolla.users.commands;

import com.esotericsoftware.kryo.NotNull;
import com.tocea.corolla.cqrs.annotations.Command;

/**
 * @author sleroy
 *
 */
@Command
public class DeleteRoleCommand {
	@NotNull
	private Integer	roleID;

	public DeleteRoleCommand() {
		super();
	}

	public DeleteRoleCommand(final Integer _roleName) {
		this.roleID = _roleName;

	}

	public Integer getRoleID() {
		return this.roleID;
	}

	public void setRoleID(final Integer _roleID) {
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
