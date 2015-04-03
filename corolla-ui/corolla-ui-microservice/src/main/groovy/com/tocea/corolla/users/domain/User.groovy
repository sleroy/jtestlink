package com.tocea.corolla.users.domain



/**
 * This class declares a member that contributes to a repository. It is
 * basically identified by the email.
 *
 * @author sleroy
 *
 */
public class User implements Serializable {

	private Integer	id

	private String	firstName

	private String	lastName

	private String	email

	private String	login

	private String	password

	private Integer		roleId

	private String	locale	= "en_GB"		//$NON-NLS-1$

	private String	activationToken	= ""		//$NON-NLS-1$

	private Integer	defaultTestProject_id

	private Date createdTime


	private boolean	active	= true

	private Integer salt

}