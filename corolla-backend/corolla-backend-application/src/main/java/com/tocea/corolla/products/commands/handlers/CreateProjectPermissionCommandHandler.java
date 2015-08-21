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
package com.tocea.corolla.products.commands.handlers;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateProjectPermissionCommand;
import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.ProjectPermission;
import com.tocea.corolla.products.exceptions.InvalidProjectPermissionInformationException;
import com.tocea.corolla.products.exceptions.ProjectPermissionAlreadyExistException;

@CommandHandler
public class CreateProjectPermissionCommandHandler implements ICommandHandler<CreateProjectPermissionCommand, ProjectPermission> {

	@Autowired
	private IProjectPermissionDAO permissionDAO;
	
	@Override
	public ProjectPermission handle(@Valid CreateProjectPermissionCommand command) {
		
		ProjectPermission permission = command.getProjectPermission();
		
		if (StringUtils.isNotEmpty(permission.getId())) {
			throw new InvalidProjectPermissionInformationException("No ID expected");
		}
		
		permission.setId(null);
			
		ProjectPermission samePermission = permissionDAO.findByProjectIdAndEntityIdAndEntityType(permission.getProjectId(), permission.getEntityId(), permission.getEntityType());
		
		if (samePermission != null) {
			throw new ProjectPermissionAlreadyExistException();
		}
		
		permissionDAO.save(permission);
		
		return permission;	
		
	}

}
