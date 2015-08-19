/*
 * Corolla - A Tool to manage software requirements and test cases 
 * Copyright (C) 2015 Tocea
 * 
 * This file is part of Corolla.
 * 
 * Corolla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, 
 * or any later version.
 * 
 * Corolla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.users.permissions;

import java.util.Set;

import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.users.domain.User;

/**
 * Builds a set of permissions that can be granted to an user
 * for a given context (global permissions, project's permissions...)
 * @author dmichel
 *
 */
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
