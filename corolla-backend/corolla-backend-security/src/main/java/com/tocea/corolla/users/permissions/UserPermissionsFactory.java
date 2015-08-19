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
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectPermission;
import com.tocea.corolla.products.domain.ProjectPermission.EntityType;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.domain.UserGroup;

/**
 * 
 * @author dmichel
 *
 */
@Component
public class UserPermissionsFactory implements IUserPermissionsFactory {

	@Autowired
	private IRoleDAO roleDAO;
	
	@Autowired
	private IUserGroupDAO groupDAO;
	
	@Autowired
	private IProjectPermissionDAO projectPermissionDAO;
	
	@Override
	public Set<String> getUserPermissions(User user) {
		
		Set<String> permissions = Sets.newHashSet();
		
		if (user != null && StringUtils.isNotEmpty(user.getRoleId())) {
			
			Role userRole = roleDAO.findOne(user.getRoleId());
			
			permissions.addAll(extractPermissions(userRole));
			
		}
		
		return permissions;
	}
	
	@Override
	public Set<String> getUserProjectPermissions(User user, Project project) {
		
		Set<String> permissions = Sets.newHashSet();
		
		if (user != null && project != null) {
			
			Collection<String> groupIds = getUserGroupIds(user);
			Collection<ProjectPermission> projectPermissions = projectPermissionDAO.findByProjectId(project.getId());
			
			for(ProjectPermission projectPermission : projectPermissions) {
				if (projectPermission.getEntityType() == EntityType.USER) {
					
					if (projectPermission.getEntityId().equals(user.getId())) {
						permissions.addAll(extractPermissions(projectPermission));
					}

				}else{
					
					if (groupIds.contains(projectPermission.getEntityId())) {				
						permissions.addAll(extractPermissions(projectPermission));			
					}
					
				}
			}
			
		}
		
		return permissions;
	}
	
	/**
	 * Retrieves the ids of the user's groups
	 * @param user
	 * @return
	 */
	private Collection<String> getUserGroupIds(User user) {
		
		List<UserGroup> groups = groupDAO.findByUserId(user.getLogin());
		
		return Collections2.transform(groups, new Function<UserGroup, String>() {
			@Override
			public String apply(UserGroup group) {
				return group != null ? group.getId() : "";
			}		
		});
	}
	
	/**
	 * Extracts permissions from ProjectPermission objects
	 * @param projectPermission
	 * @return
	 */
	private Set<String> extractPermissions(ProjectPermission projectPermission) {
		
		Set<String> permissions = Sets.newHashSet();
		
		for (String roleId : projectPermission.getRoleIds()) {
			
			Role role = roleDAO.findOne(roleId);
			
			permissions.addAll(extractPermissions(role));
			
		}
		
		return permissions;
	}
	
	/**
	 * Reads role's permissions
	 * @param role
	 * @return
	 */
	private Set<String> extractPermissions(Role role) {
		
		Set<String> permissions = Sets.newHashSet();
		
		if (role != null && StringUtils.isNotEmpty(role.getPermissions())) {
			for (String permission : role.getPermissions().split(", ")) {
				permissions.add(permission);
			}
		}
		
		return permissions;
	}
	
}
