/**
 *
 */
package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.dto.UserPasswordDto;

@Command
public class CreateUserCommand {
	@NotNull
	private User	user;

	public CreateUserCommand() {
		super();
	}

	public CreateUserCommand(final User _user) {
		this.user = _user;

	}

	public CreateUserCommand(final UserPasswordDto _dto) {
		this.user = new User();
		this.user.setActivationToken(_dto.getActivationToken());
		this.user.setActive(_dto.isActive());
		this.user.setCreatedTime(_dto.getCreatedTime());
		this.user.setEmail(_dto.getEmail());
		this.user.setFirstName(_dto.getFirstName());
		this.user.setLastName(_dto.getLastName());
		this.user.setId(_dto.getId());
		this.user.setLocale(_dto.getLocale());
		this.user.setLocaleIfNecessary();
		this.user.setLogin(_dto.getLogin());
		this.user.setPassword(_dto.getPassword());
		this.user.setRoleId(_dto.getRoleId());

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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreateUserCommand [user=" + this.user + "]";
	}
}
