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
package com.tocea.corolla.products.validation;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectPermission;
import com.tocea.corolla.products.domain.ProjectPermission.EntityType;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.domain.UserGroup;
import com.tocea.corolla.users.service.AuthenticationUserService;

@Component("projectSecurityService")
public class ProjectSecurityService implements IProjectSecurityService {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IRoleDAO roleDAO;
	
	@Autowired
	private IUserGroupDAO groupDAO;
	
	@Autowired
	private IProjectPermissionDAO projectPermissionDAO;
	
	@Autowired
	private AuthenticationUserService authService;
	
	@Override
	public boolean hasPermission(String projectKey, String right) {
		
		User user = authService.getAuthenticatedUser();
		
		if (user == null) {
			return false;
		}
		
		Project project = projectDAO.findByKey(projectKey); 
		
		if (project == null) {
			return true;
		}
						
		List<UserGroup> groups = groupDAO.findByUserId(user.getLogin());
		Collection<String> groupIds = Collections2.transform(groups, new Function<UserGroup, String>() {
			@Override
			public String apply(UserGroup group) {
				return group != null ? group.getId() : "";
			}		
		});
		
		List<ProjectPermission> permissions = projectPermissionDAO.findByProjectId(project.getId());
		
		for(ProjectPermission permission : permissions) {
			if (permission.getEntityType() == EntityType.USER) {
				
				if (permission.getEntityId().equals(user.getId())) {
					if (checkPermission(permission, right)) {
						return true;
					}
				}

			}else{
				
				if (groupIds.contains(permission.getEntityId())) {				
					if (checkPermission(permission, right)) {
						return true;
					}				
				}
				
			}
		}
		
		return false;
	}
	
	private boolean checkPermission(ProjectPermission permission, String right) {
		
		for (String roleId : permission.getRoleIds()) {
			
			Role role = roleDAO.findOne(roleId);
			
			if (role != null && StringUtils.isNotEmpty(role.getPermissions())) {
				return role.getPermissions().contains(right);
			}	
			
		}
		
		return false;
	}

	@Override
	public List<String> allowedProjectIds() {
	
		User user = authService.getAuthenticatedUser();
		
		List<String> ids = Lists.newArrayList();
		
		if (user != null) {
					
			List<ProjectPermission> permissions = projectPermissionDAO.findByEntityIdAndEntityType(user.getId(), EntityType.USER);					
			ids.addAll(Collections2.transform(permissions, new ProjectPermissionToProjectIdFunction()));
			
			List<UserGroup> groups = groupDAO.findByUserId(user.getId());
			
			for(UserGroup group : groups) {
				permissions = projectPermissionDAO.findByEntityIdAndEntityType(group.getId(), EntityType.GROUP);
				ids.addAll(Collections2.transform(permissions, new ProjectPermissionToProjectIdFunction()));
			}

		}
		
		return ImmutableSet.copyOf(Iterables.filter(ids, Predicates.not(Predicates.isNull()))).asList();		
	}
	
	private static class ProjectPermissionToProjectIdFunction implements Function<ProjectPermission, String> {
		
		@Override
		public String apply(ProjectPermission permission) {
			if (permission != null) {
				return permission.getProjectId();
			}
			return null;
		}
		
	}
	
}
