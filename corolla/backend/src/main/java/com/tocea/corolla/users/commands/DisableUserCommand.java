/**
 *
 */
package com.tocea.corolla.users.commands;

import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class DisableUserCommand {
	private String	userLogin;

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
		return "DisableUserCommand [userLogin=" + this.userLogin + "]";
	}
}
