package com.tocea.corolla.users.permissions;

import java.util.Set;

import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.users.domain.User;

public interface IUserPermissionsFactory {

	/**
	 * Retrieves all global permissions of an user
	 * @param user
	 * @return
	 */
	Set<String> getUserPermissions(User user);

	/**
	 * Retrieves all permissions of an user
	 * that can be applied to a given project
	 * @param user
	 * @param project
	 * @return
	 */
	Set<String> getUserProjectPermissions(User user, Project project);

}
