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
package com.tocea.corolla.products.dto;

import java.util.List;

import com.tocea.corolla.products.domain.ProjectPermission;
import com.tocea.corolla.products.domain.ProjectPermission.EntityType;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.domain.UserGroup;

public class ProjectPermissionDTO {

	private String id;
	
	private String entityName;
	
	private String entityID;
	
	private EntityType entityType;
	
	private List<Role> roles;

	public ProjectPermissionDTO() {
		super();
	}
	
	public ProjectPermissionDTO(ProjectPermission permission, User user, List<Role> roles) {
		this(permission, roles);
		this.entityName = user.getFirstName()+" "+user.getLastName();
	}
	
	public ProjectPermissionDTO(ProjectPermission permission, UserGroup group, List<Role> roles) {
		this(permission, roles);
		this.entityName = group.getName();
	}
	
	public ProjectPermissionDTO(ProjectPermission permission, List<Role> roles) {
		super();
		this.id = permission.getId();
		this.entityID = permission.getEntityId();
		this.entityType = permission.getEntityType();
		this.roles = roles;	
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityID() {
		return entityID;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
