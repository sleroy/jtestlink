/**
 *
 */
package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;

/**
 * @author sleroy
 *
 */
@Command
public class MarksRoleAsDefaultCommand {
	@NotNull
	private String	roleID;

	public MarksRoleAsDefaultCommand() {
		super();
	}

	public MarksRoleAsDefaultCommand(final String _roleName) {
		setRoleID(_roleName);
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
	public void setRoleID(final Integer _roleName) {
		this.roleID = _roleName.toString();
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
		return "MarksRoleAsDefaultCommand [roleID=" + this.roleID + "]";
	}

}
