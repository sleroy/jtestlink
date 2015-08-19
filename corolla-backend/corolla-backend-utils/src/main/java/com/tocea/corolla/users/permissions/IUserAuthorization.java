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

import java.util.Iterator;
import java.util.List;

/**
 * Defines authorizations that can be granted
 * to the current authenticated user
 * 
 * @author dmichel
 *
 */
public interface IUserAuthorization {

	/**
	 * Checks if the current user can view the portfolio tree
	 * @return
	 */
	boolean hasPortfolioReadAccess();

	/**
	 * Checks if the current user has write access on the portfolio tree
	 * (create/move/delete nodes...)
	 * @return
	 */
	boolean hasPortfolioWriteAccess();

	/**
	 * Checks if the current user has the right to create projects
	 * @return
	 */
	boolean canCreateProjects();

	/**
	 * Checks if the current user has the right to delete projects
	 * @return
	 */
	boolean canDeleteProjects();

	/**
	 * Checks if the current user can edit a project
	 * @param projectKey
	 * @return
	 */
	boolean canEditProject(String projectKey);

	/**
	 * Checks if the current user can view project's data
	 * @param projectKey
	 * @return
	 */
	boolean canReadProject(String projectKey);

	/**
	 * Checks if the current user can view the requirements
	 * of a specific project
	 * @param projectKey
	 * @return
	 */
	boolean hasRequirementReadAccess(String projectKey);

	/**
	 * Checks if the current user can edit the requirements
	 * of a specific project
	 * @param projectKey
	 * @return
	 */
	boolean hasRequirementWriteAccess(String projectKey);

	/**
	 * Checks if the current user can generate requirements reports
	 * in a specific project
	 * @param projectKey
	 * @return
	 */
	boolean hasRequirementReportAccess(String projectKey);

	/**
	 * Checks if the current user is admin
	 * @return
	 */
	boolean hasAdminAccess();

	/**
	 * Checks if the current user has the right to access
	 * system configuration
	 * @return
	 */
	boolean hasSystemConfigurationAccess();
	
	/**
	 * Checks custom permissions
	 * @param _permission
	 * @return
	 */
	boolean hasCustomPermission(String _permission);
	
	/**
	 * Filters projects with a list of permissions
	 * that the current user need to have
	 * @param _projects
	 * @param permissions
	 * @return
	 */
	Iterator<String> filterAllowedProjects(Iterator<String> _projects, List<String> permissions);
	
}
