/**
 *
 */
package com.tocea.corolla.users.domain;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Defines permissions employed in authorization mechanism.
 *
 * @author sleroy
 *
 */
public class Permission {
	
	public static final String	REST				= "ROLE_REST";
	public static final String	REQUIREMENT			= "ROLE_REQUIREMENT";
	public static final String	APPLICATION			= "ROLE_APPLICATION";
	public static final String	TESTSUITE			= "ROLE_TESTSUITE";
	public static final String	TESTCAMP			= "ROLE_TESTCAMP";
	// Need fine-grained permissions
	public static final String	ADMIN				= "ROLE_ADMIN";
	public static final String	ADMIN_USERS			= "ROLE_ADMIN_USERS";
	public static final String	ADMIN_USER_GROUPS	= "ROLE_ADMIN_USER_GROUPS";
	public static final String	ADMIN_ROLES			= "ROLE_ADMIN_ROLES";
	public static final String	ADMIN_CONFIG		= "ROLE_ADMIN_CONFIG";
	
	public static final String PROJECT_WRITE 		= "ROLE_PROJECT_WRITE";
	public static final String PROJECT_READ 		= "ROLE_PROJECT_READ";
	
	/**
	 * For managing a project (edit project's data, create/delete branches...)
	 */
	public static final String PROJECT_MANAGEMENT	= "ROLE_PROJECT_MANAGEMENT";
	
	/**
	 * For managing the portfolio tree (create/move/edit/delete nodes, create projects, etc..)
	 */
	public static final String PORTFOLIO_MANAGEMENT = "ROLE_PORTFOLIO_MANAGEMENT";
	
	/**
	 * Retrieve the list of all existing permissions
	 * @return
	 */
	public static List<String> list() {
		
		List<String> permissions = Lists.newArrayList();
		for (Field f : Permission.class.getDeclaredFields()) {
			try {
				permissions.add((String) f.get(null));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return permissions;
	}
	
}
