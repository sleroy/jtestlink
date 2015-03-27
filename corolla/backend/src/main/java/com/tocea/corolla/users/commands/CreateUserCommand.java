/**
 *
 */
package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.users.domain.User;

@Command
public class CreateUserCommand {
	@NotNull
	private User	user;

	/**
	 *
	 */
	public CreateUserCommand() {
		super();
	}

	/**
	 * @param _user
	 */
	public CreateUserCommand(final User _user) {
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
		return "CreateUserCommand [user=" + this.user + "]";
	}
}
