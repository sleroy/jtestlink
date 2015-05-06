/**
 *
 */
package com.tocea.corolla.users.domain;

/**
 * Defines permissions employed in authorization mechanism.
 *
 * @author sleroy
 *
 */
public class Permission {
	public static final String	REST			= "ROLE_REST";
	public static final String	REQUIREMENT		= "ROLE_REQUIREMENT";
	public static final String	APPLICATION		= "ROLE_APPLICATION";
	public static final String	TESTSUITE		= "ROLE_TESTSUITE";
	public static final String	TESTCAMP		= "ROLE_TESTCAMP";
	// Need fine-grained permissions
	public static final String	ADMIN			= "ROLE_ADMIN";
	public static final String	ADMIN_USERS		= "ROLE_ADMIN_USERS";
	public static final String	ADMIN_ROLES		= "ROLE_ADMIN_ROLES";
	public static final String	ADMIN_CONFIG	= "ROLE_ADMIN_CONFIG";
}
