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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.users.service.AuthenticationUserService;

/**
 * 
 * @author dmichel
 *
 */
@Service
public class UserLowAuthorizationService implements IUserLowAuthorization {
	
	@Autowired
	private AuthenticationUserService authService;
	
	@Autowired
	private IUserPermissionsFactory permissionFactory;
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@Override
	public boolean hasPermission(String permission) {
		
		Set<String> permissions = permissionFactory.getUserPermissions(authService.getAuthenticatedUser());
		
		return permissions.contains(permission);
	}

	@Override
	public boolean hasProjectPermission(String projectKey, String permission) {
		
		Project project = projectDAO.findByKey(projectKey);
		
		Set<String> permissions = permissionFactory.getUserProjectPermissions(authService.getAuthenticatedUser(), project);
		
		return permissions.contains(permission);
		
	}

	@Override
	public Iterator<String> filterAllowedProjects(Iterator<String> projectKeys, List<String> permissions) {
		
		Collection<String> allowedProjects = Lists.newArrayList();
		
		while(projectKeys.hasNext()) {
			
			String key = projectKeys.next();
			Project project = projectDAO.findByKey(key);
			Set<String> projectPermissions = permissionFactory.getUserProjectPermissions(authService.getAuthenticatedUser(), project);
			
			if (projectPermissions.containsAll(permissions)) {
				allowedProjects.add(key);
			}
			
		}
		
		return allowedProjects.iterator();
	}

}
