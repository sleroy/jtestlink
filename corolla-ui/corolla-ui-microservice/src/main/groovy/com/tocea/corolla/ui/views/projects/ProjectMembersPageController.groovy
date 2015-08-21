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
package com.tocea.corolla.ui.views.projects;

import javax.validation.Valid;

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.tocea.corolla.cqrs.gate.CommandExecutionException;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.CreateProjectPermissionCommand
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectPermission;
import com.tocea.corolla.products.domain.ProjectPermission.EntityType;
import com.tocea.corolla.products.exceptions.ProjectNotFoundException
import com.tocea.corolla.products.exceptions.ProjectPermissionAlreadyExistException;
import com.tocea.corolla.products.exceptions.ProjectPermissionNotFoundException
import com.tocea.corolla.users.dao.IRoleDAO;

@Controller
@Slf4j
public class ProjectMembersPageController {

	private static final String PROJECT_PERMISSION_FORM = "project/permission_form"
		
	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IProjectPermissionDAO permissionDAO;
	
	@Autowired
	private IRoleDAO roleDAO;
	
	@Autowired
	private Gate gate;
	
	@RequestMapping(value = "/ui/projects/{projectKey}/members/add")
	@PreAuthorize("@userAuthorization.canEditProject(#projectKey)")
	public ModelAndView getAddPage(@PathVariable String projectKey) {
		
		def project = findProjectOrFail(projectKey)	
		def permission = new ProjectPermission(projectId: project.id)
		
		return buildFormData(project, permission)
	}
	
	@RequestMapping(value = "/ui/projects/{projectKey}/members/add", method = RequestMethod.POST)
	@PreAuthorize("@userAuthorization.canEditProject(#projectKey)")
	public ModelAndView addPermission(@PathVariable String projectKey, @Valid @ModelAttribute("permission") ProjectPermission permission, BindingResult _result) {
		
		def project = findProjectOrFail(projectKey)	
		permission = _result.model.get("permission")
				
		if (_result.hasErrors()) {
			return buildFormData(project, permission)
		}
		
		try {
			
			gate.dispatch new CreateProjectPermissionCommand(permission)
			
		}catch(CommandExecutionException ex) {
			
			if (ex.cause instanceof ProjectPermissionAlreadyExistException) {
				_result.rejectValue("entityId", "error.entityId", "A permission is already defined for this user / group.")
				return buildFormData(project, permission)
			}else{
				throw ex
			}
			
		}
		
		return new ModelAndView("redirect:/ui/projects/"+project.key+"#members")
	}
	
	@RequestMapping(value = "/ui/projects/{projectKey}/members/edit/{permissionID}")
	@PreAuthorize("@userAuthorization.canEditProject(#projectKey)")
	public ModelAndView getEditPage(@PathVariable String projectKey, @PathVariable String permissionID) {
		
		def project = findProjectOrFail(projectKey)	
		def permission = permissionDAO.findOne(permissionID)
		
		if (permission == null) {
			throw new ProjectPermissionNotFoundException();
		}
		
		return buildFormData(project, permission)
		
	}
	
	private ModelAndView buildFormData(Project project, ProjectPermission permission) {
		
		def roles = roleDAO.findAll()
		
		def model = new ModelAndView(PROJECT_PERMISSION_FORM)
		model.addObject "project", project
		model.addObject "permission", permission
		model.addObject "entityTypes", EntityType.values()
		model.addObject "roles", roles
		
		return model
	}
	
	private Project findProjectOrFail(String projectKey) {
		
		def project = projectDAO.findByKey(projectKey)
				
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		
		return project
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler([
	      ProjectNotFoundException.class,
	      ProjectPermissionNotFoundException.class
	])
	public void handlePageNotFoundException() {
		
	}
	
}
