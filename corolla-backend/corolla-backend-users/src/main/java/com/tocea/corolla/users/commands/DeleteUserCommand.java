/**
 *
 */
package com.tocea.corolla.users.commands;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class DeleteUserCommand {
	@NotBlank
	private String	userLogin;

	public DeleteUserCommand() {
		super();
	}

	/**
	 * @param _userLogin
	 */
	public DeleteUserCommand(final String _userLogin) {
		super();
		this.userLogin = _userLogin;
	}

	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return this.userLogin;
	}

	/**
	 * @param _userLogin
	 *            the userLogin to set
	 */
	public void setUserLogin(final String _userLogin) {
		this.userLogin = _userLogin;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeleteUserCommand [user=" + this.userLogin + "]";
	}
}
