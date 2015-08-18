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

public interface IUserAuthorization {

	boolean hasPortfolioReadAccess();

	boolean hasPortfolioWriteAccess();

	boolean canCreateProjects();

	boolean canDeleteProjects();

	boolean canEditProject(String projectKey);

	boolean canReadProject(String projectKey);

	boolean hasRequirementReadAccess(String projectKey);

	boolean hasRequirementWriteAccess(String projectKey);

	boolean hasRequirementReportAccess(String projectKey);

	boolean hasAdminAccess();

	boolean hasSystemConfigurationAccess();
	
	boolean hasCustomPermission(String _permission);
	
	Iterator<String> filterAllowedProjects(Iterator<String> _projects, List<String> permissions);
	
}
