/**
 *
 */
package com.tocea.corolla.users.commands;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.cqrs.annotations.Command;

/**
 * @author sleroy
 *
 */
@Command
public class DeleteRoleCommand {
	@NotBlank
	private String	roleName;

	public DeleteRoleCommand() {
		super();
	}

	public DeleteRoleCommand(final String _roleName) {
		this.roleName = _roleName;

	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return this.roleName;
	}

	/**
	 * @param _roleName
	 *            the roleName to set
	 */
	public void setRoleName(final String _roleName) {
		this.roleName = _roleName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeleteRoleCommand [roleName=" + this.roleName + "]";
	}

}
