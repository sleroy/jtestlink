/**
 *
 */
package com.tocea.corolla.users.dto;

import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;

/**
 * @author sleroy
 *
 */
public class UserWithRoleDto extends UserDto {
	private String	role;

	public UserWithRoleDto(final User _user, final Role _role) {
		super(_user);
		this.role = _role == null ? "" : _role.getName();
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return this.role;
	}

	/**
	 * @param _role
	 *            the role to set
	 */
	public void setRole(final String _role) {
		this.role = _role;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserWithRoleDto [role=" + this.role + ", toString()="
				+ super.toString() + "]";
	}
}
