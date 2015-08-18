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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userAuthorization")
public class UserAuthorizationService implements IUserAuthorization {

	@Autowired
	private IUserLowAuthorization lowerService;
	
	@Override
	public boolean hasPortfolioReadAccess() {
		
		return lowerService.hasPermission(Permissions.PORTFOLIO_READ);
	}

	@Override
	public boolean hasPortfolioWriteAccess() {
		
		return lowerService.hasPermission(Permissions.PORTFOLIO_WRITE);
	}

	@Override
	public boolean canCreateProjects() {
		
		return lowerService.hasPermission(Permissions.PROJECT_CREATE);
	}
	
	@Override
	public boolean canDeleteProjects() {
		
		return lowerService.hasPermission(Permissions.PROJECT_DELETE);
	}

	@Override
	public boolean canEditProject(String projectKey) {
		
		return lowerService.hasProjectPermission(projectKey, Permissions.PROJECT_EDIT);
	}

	@Override
	public boolean canReadProject(String projectKey) {
		
		return lowerService.hasProjectPermission(projectKey, Permissions.PROJECT_READ);
	}

	@Override
	public boolean hasRequirementReadAccess(String projectKey) {
		
		return lowerService.hasProjectPermission(projectKey, Permissions.REQUIREMENTS_READ);
	}

	@Override
	public boolean hasRequirementWriteAccess(String projectKey) {
		
		return lowerService.hasProjectPermission(projectKey, Permissions.REQUIREMENTS_WRITE);
	}

	@Override
	public boolean hasRequirementReportAccess(String projectKey) {
		
		return lowerService.hasProjectPermission(projectKey, Permissions.REQUIREMENTS_REPORT);
	}

	@Override
	public boolean hasAdminAccess() {
		return lowerService.hasPermission(Permissions.ADMIN);
	}

	@Override
	public boolean hasSystemConfigurationAccess() {
		return lowerService.hasPermission(Permissions.ADMIN_CONFIG);
	}

	@Override
	public boolean hasCustomPermission(String _permission) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<String> filterAllowedProjects(Iterator<String> projects, List<String> permissions) {
		
		return lowerService.filterAllowedProjects(projects, permissions);
	}

}
