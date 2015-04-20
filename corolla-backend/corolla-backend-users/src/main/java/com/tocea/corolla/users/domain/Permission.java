/**
 *
 */
package com.tocea.corolla.users.domain;

/**
 * Defines permissions employed in authorization mechanism.
 * @author sleroy
 *
 */
public class Permission {
	public static final String GUI= "ROLE_GUI";
	public static final String REST= "ROLE_REST";
	// Need fine-grained permissions
	public static final String ADMIN= "ROLE_ADMIN";
	public static final String ADMIN_USERS= "ROLE_ADMIN_USERS";
}

