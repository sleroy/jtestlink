package com.tocea.corolla.users.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * This class declares a member that contributes to a repository. It is
 * basically identified by the email.
 *
 * @author sleroy
 *
 */
public class User implements Serializable {

	private Integer	id;

	private String	firstName;

	private String	lastName;

	private String	email;

	private String	login;

	private String	password;

	private Integer		roleId;

	private String	locale	= "en_GB";		//$NON-NLS-1$

	private String	activationToken	= "";		//$NON-NLS-1$

	private Integer	defaultTestProject_id;

	private Date createdTime;


	private boolean	active	= true;

	private Integer salt;

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
	 * @return the defaultTestProject_id
	 */
	public Integer getDefaultTestProject_id() {
		return this.defaultTestProject_id;
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
	 * @return the salt
	 */
	public Integer getSalt() {
		return this.salt;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active;
	}

	/**
	 * @param _activationToken the activationToken to set
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
	 * @param _createdTime the createdTime to set
	 */
	public void setCreatedTime(final Date _createdTime) {
		this.createdTime = _createdTime;
	}

	/**
	 * @param _defaultTestProject_id
	 *            the defaultTestProject_id to set
	 */
	public void setDefaultTestProject_id(final Integer _defaultTestProject_id) {
		this.defaultTestProject_id = _defaultTestProject_id;
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

	/**
	 * @param _role_id
	 *            the role_id to set
	 */
	public void setRoleId(final Integer _role_id) {
		this.roleId = _role_id;
	}

	/**
	 * @param _salt the salt to set
	 */
	public void setSalt(final Integer _salt) {
		this.salt = _salt;
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