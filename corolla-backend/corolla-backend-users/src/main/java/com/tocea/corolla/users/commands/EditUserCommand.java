/**
 *
 */
package com.tocea.corolla.users.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.dto.UserPasswordDto;
import com.tocea.corolla.users.dto.UserProfileDto;

@Command
public class EditUserCommand {
	@NotNull
	private User	user;

	public EditUserCommand() {
		super();
	}

	public EditUserCommand(final User _user) {
		this.user = _user;

	}

	public EditUserCommand(final User _user, final UserProfileDto _dto) {
		this.user = _user;
		this.user.setFirstName(_dto.getFirstName());
		this.user.setLastName(_dto.getLastName());
		this.user.setEmail(_dto.getEmail());
		this.user.setLocale(_dto.getLocale());
		this.user.setActivationToken(_dto.getActivationToken());
		this.user.setPassword(_dto.getPassword());

	}

	public EditUserCommand(final UserPasswordDto _dto) {
		this.user = new User();
		this.user.setActivationToken(_dto.getActivationToken());
		this.user.setActive(_dto.isActive());
		this.user.setCreatedTime(_dto.getCreatedTime());
		this.user.setEmail(_dto.getEmail());
		this.user.setFirstName(_dto.getFirstName());
		this.user.setLastName(_dto.getLastName());
		this.user.setLocale(_dto.getLocale());
		this.user.setLocaleIfNecessary();
		this.user.setLogin(_dto.getLogin());
		this.user.setPassword(_dto.getPassword());
		this.user.setRoleId(_dto.getRoleId());
		this.user.setId(_dto.getId());

	}

	public User getUser() {
		return this.user;
	}

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
		return "EditUserCommand [user=" + this.user + "]";
	}
}
