/**
 *
 */
package com.tocea.corolla.users.commands;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.users.domain.User;

@Command
public class AddNewUserCommand {
	private User	user;

	/**
	 *
	 */
	public AddNewUserCommand() {
		super();
	}

	/**
	 * @param _user
	 */
	public AddNewUserCommand(final User _user) {
		this.user = _user;

	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * @param _user
	 *            the user to set
	 */
	public void setUser(final User _user) {
		this.user = _user;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddNewUserCommand [user=" + this.user + "]";
	}
}
