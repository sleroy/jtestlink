package com.tocea.corolla.users.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Strings;

/**
 * This class declares a member that contributes to a repository. It is
 * basically identified by the email.
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "users", indexes = { @Index(unique = true, name = "login_index", columnList = "login") })
public class User implements Serializable {

	@Id
	@GeneratedValue
	private Integer	id;

	@NotBlank
	@Size(max = 40)
	@Column(nullable = false, length = 40)
	private String	firstName		= "";

	@NotBlank
	@Size(max = 40)
	@Column(nullable = false, length = 40)
	private String	lastName		= "";

	@NotBlank
	@Size(max = 128)
	@Column(nullable = false, length = 128)
	@Email
	private String	email			= "";

	@NotBlank
	@Size(min = 3, max = 30)
	@Column(nullable = false, length = 30)
	private String	login			= "";

	@NotBlank
	@Size(max = 256)
	@Column(nullable = false, length = 256)
	private String	password		= "";

	@NotNull
	@Column(nullable = false)
	private Integer	roleId;

	@NotBlank
	@Size(max = 10)
	@Column(nullable = false, length = 10)
	private String	locale			= "en_GB";	//$NON-NLS-1$

	@Size(max = 50)
	@Column(nullable = true, length = 50)
	private String	activationToken	= "";		//$NON-NLS-1$



	@NotNull
	@Column(nullable = false)
	private Date	createdTime;

	@NotNull
	@Column(nullable = false)
	private boolean	active			= true;

	/**
	 * Copy values if missing fields
	 */
	public void copyMissingFields() {
		if (this.activationToken == null) {
			this.activationToken = "";
		}
		if (Strings.isNullOrEmpty(this.firstName)
				&& Strings.isNullOrEmpty(this.lastName)) {
			this.firstName = this.login;
		}
		if (this.createdTime == null) {
			this.createdTime = new Date();
		}

		this.setLocaleIfNecessary();

	}

	/**
	 * @return the activationToken
	 */
	public String getActivationToken() {
		return this.activationToken;
	}

	/**
	 * @return the createdTime
	 */
	public Date getCreatedTime() {
		return this.createdTime;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return this.locale;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @return the role_id
	 */
	public Integer getRoleId() {
		return this.roleId;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @param _activationToken
	 *            the activationToken to set
	 */
	public void setActivationToken(final String _activationToken) {
		this.activationToken = _activationToken;
	}

	/**
	 * @param _active
	 *            the active to set
	 */
	public void setActive(final boolean _active) {
		this.active = _active;
	}

	/**
	 * @param _createdTime
	 *            the createdTime to set
	 */
	public void setCreatedTime(final Date _createdTime) {
		this.createdTime = _createdTime;
	}

	/**
	 * @param _email
	 *            the email to set
	 */
	public void setEmail(final String _email) {
		this.email = _email;
	}

	/**
	 * @param _firstName
	 *            the firstName to set
	 */
	public void setFirstName(final String _firstName) {
		this.firstName = _firstName;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _lastName
	 *            the lastName to set
	 */
	public void setLastName(final String _lastName) {
		this.lastName = _lastName;
	}

	/**
	 * @param _locale
	 *            the locale to set
	 */
	public void setLocale(final String _locale) {
		this.locale = _locale;
	}

	/**
	 * Set locale if necessary
	 *
	 * @param _user
	 */
	public void setLocaleIfNecessary() {
		if (Strings.isNullOrEmpty(this.getLocale())) {
			this.setLocale(Locale.getDefault().toString());
		}

	}

	/**
	 * @param _login
	 *            the login to set
	 */
	public void setLogin(final String _login) {
		this.login = _login;
	}

	/**
	 * @param _password
	 *            the password to set
	 */
	public void setPassword(final String _password) {
		this.password = _password;
	}

	public void setRole(final Role _role) {
		this.roleId = _role.getId();
	}

	/**
	 * @param _role_id
	 *            the role_id to set
	 */
	public void setRoleId(final Integer _role_id) {
		this.roleId = _role_id;
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + this.id + ", firstName=" + this.firstName
				+ ", lastName=" + this.lastName + ", email=" + this.email
				+ ", login=" + this.login + "]";
	}

}