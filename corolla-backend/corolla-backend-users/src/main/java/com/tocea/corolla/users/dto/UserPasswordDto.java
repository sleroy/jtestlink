/**
 *
 */
package com.tocea.corolla.users.dto;

import javax.validation.constraints.AssertTrue;
import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.users.domain.User;

/**
 * @author sleroy
 *
 */

public class UserPasswordDto extends UserDto {

	@NotBlank
	private String	password;

	@NotBlank
	private String	passwordConfirm;

	public UserPasswordDto() {
	}

	public UserPasswordDto(final User _user) {
		super(_user);
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return the passwordConfirm
	 */
	public String getPasswordConfirm() {
		return this.passwordConfirm;
	}

	@AssertTrue(message = "Password and confirmation should be identical")
	public boolean isValid() {
		return this.password.equals(this.passwordConfirm);
	}

	/**
	 * @param _password
	 *            the password to set
	 */
	public void setPassword(final String _password) {
		this.password = _password;
	}

	/**
	 * @param _passwordConfirm
	 *            the passwordConfirm to set
	 */
	public void setPasswordConfirm(final String _passwordConfirm) {
		this.passwordConfirm = _passwordConfirm;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserPasswordDto [password=" + this.password
				+ ", passwordConfirm=" + this.passwordConfirm + ", toString()="
				+ super.toString() + "]";
	}

}
