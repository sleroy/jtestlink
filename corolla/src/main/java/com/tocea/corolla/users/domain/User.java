package com.tocea.corolla.users.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


/**
 * This class declares a member that contributes to a repository. It is
 * basically identified by the email.
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue
	private Integer	id;

	@NotBlank
	@Column(nullable = false, length = 40)
	private String	firstName;

	@NotBlank
	@Column(nullable = false, length = 40)
	private String	lastName;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String	email;

	@NotBlank
	@Column(nullable = false, length = 30)
	private String	login;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String	password;

	@NotNull
	@Column(nullable = false)
	private int		role_id;

	@NotBlank
	@Column(nullable = false, length = 10)
	private String	locale	= "en_GB";		//$NON-NLS-1$

	@Column(nullable = true)
	private Integer	defaultTestProject_id;

	@NotNull
	@Column(nullable = false)
	private Date createdTime;


	@NotNull
	@Column(nullable = false)
	private boolean	active	= true;

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
	public int getRole_id() {
		return this.role_id;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return this.active;
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
	public void setRole_id(final int _role_id) {
		this.role_id = _role_id;
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