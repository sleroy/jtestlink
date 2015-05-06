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
public class MarksRoleAsDefaultCommand {
	@NotNull
	private Integer	roleID;

	public MarksRoleAsDefaultCommand() {
		super();
	}

	public MarksRoleAsDefaultCommand(final Integer _roleName) {
		this.roleID = _roleName;

	}

	/**
	 * @return the roleID
	 */
	public Integer getRoleID() {
		return this.roleID;
	}

	/**
	 * @param _roleName
	 *            the roleID to set
	 */
	public void setRoleID(final Integer _roleName) {
		this.roleID = _roleName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MarksRoleAsDefaultCommand [roleID=" + this.roleID + "]";
	}

}
