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
package com.tocea.corolla.products.commands;

import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectPermission;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.domain.UserGroup;

@CommandOptions
public class CreateProjectPermissionCommand {

	@NotNull
	private ProjectPermission projectPermission;
	
	public CreateProjectPermissionCommand() {
		super();
	}
	
	public CreateProjectPermissionCommand(@Valid ProjectPermission projectPermission) {
		this.projectPermission = projectPermission;
	}
	
	public CreateProjectPermissionCommand(Project project, User user, Collection<String> roleIds) {
		this.projectPermission = new ProjectPermission();
		this.projectPermission.setProjectId(project.getId());
		this.projectPermission.setEntityId(user.getId());
		this.projectPermission.setEntityType(ProjectPermission.EntityType.USER);
		this.projectPermission.setRoleIds(Lists.newArrayList(roleIds));
	}
	
	public CreateProjectPermissionCommand(Project project, UserGroup group, Collection<String> roleIds) {
		this.projectPermission = new ProjectPermission();
		this.projectPermission.setProjectId(project.getId());
		this.projectPermission.setEntityId(group.getId());
		this.projectPermission.setEntityType(ProjectPermission.EntityType.GROUP);
		this.projectPermission.setRoleIds(Lists.newArrayList(roleIds));
	}

	public ProjectPermission getProjectPermission() {
		return projectPermission;
	}

	public void setProjectPermission(ProjectPermission projectPermission) {
		this.projectPermission = projectPermission;
	}
	
}
