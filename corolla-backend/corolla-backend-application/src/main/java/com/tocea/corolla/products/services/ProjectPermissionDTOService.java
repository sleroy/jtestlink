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
package com.tocea.corolla.products.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.ProjectPermission;
import com.tocea.corolla.products.domain.ProjectPermission.EntityType;
import com.tocea.corolla.products.dto.ProjectPermissionDTO;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.domain.UserGroup;

@Service
public class ProjectPermissionDTOService implements IProjectPermissionDTOService {

	@Autowired
	private IProjectPermissionDAO permissionDAO;
	
	@Autowired
	private IRoleDAO roleDAO;
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IUserGroupDAO groupDAO;
	
	@Override
	public List<ProjectPermissionDTO> getProjectPermissionDTOList(String projectID) {
		
		List<ProjectPermissionDTO> result = Lists.newArrayList();
		
		Collection<ProjectPermission> permissions = permissionDAO.findByProjectId(projectID);
		
		for(ProjectPermission permission : permissions) {
			
			List<Role> roles = Lists.newArrayList(roleDAO.findAll(permission.getRoleIds()));
			
			if (EntityType.USER.equals(permission.getEntityType())) {
				User user = userDAO.findOne(permission.getEntityId());
				result.add(new ProjectPermissionDTO(permission, user, roles));
			}else{
				UserGroup group = groupDAO.findOne(permission.getEntityId());
				result.add(new ProjectPermissionDTO(permission, group, roles));
			}
					
		}
		
		return result;
	}

}
