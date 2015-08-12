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
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.products.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.CommandExecutionException;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.DeleteProjectBranchCommand;
import com.tocea.corolla.products.commands.EditProjectCommand;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException;
import com.tocea.corolla.products.exceptions.ProjectNotFoundException;
import com.tocea.corolla.products.utils.ProjectUtils;
import com.tocea.corolla.users.domain.Permission;

@RestController
@RequestMapping("/rest/projects")
public class ProjectRestController {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired
	private Gate gate;
	
	@RequestMapping(value = "/all")
	@PreAuthorize("isAuthenticated()")
	public Collection<Project> findAll() {
		
		return projectDAO.findAll();
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public Collection<Project> findAll(@RequestBody List<String> ids) {
		
		return (Collection<Project>) projectDAO.findAll(ids);
	}
	
	@RequestMapping(value="/tags")
	@PreAuthorize("isAuthenticated()")
	public Collection<String> findAllTags() {
		
		Collection<Project> projects = projectDAO.findAll();
		return Lists.newArrayList(ProjectUtils.extractTags(projects));
	}
	
	@RequestMapping(value="/{projectKey}/tags")
	@PreAuthorize("isAuthenticated()")
	public Collection<String> findTags(@PathVariable String projectKey) {
		
		Project project = projectDAO.findByKey(projectKey);
		
		if (project != null && project.getTags() != null) {			
			return project.getTags();
		}
		
		return Lists.newArrayList();
	}
	
	@RequestMapping(value="/{projectKey}/tags/push", method = RequestMethod.POST)
	@Secured({ Permission.PROJECT_MANAGEMENT })
	public Collection<String> pushTags(@PathVariable String projectKey, @RequestBody String data) throws UnsupportedEncodingException {
		
		Project project = projectDAO.findByKey(projectKey);
		
		if (project != null) {
			
			data = data.replace("tags=", "");
			data = URLDecoder.decode(data, "UTF-8");
			List<String> tags = Lists.newArrayList(data.split(","));
			
			project.setTags(tags);
			
			gate.dispatch(new EditProjectCommand(project));
			
			return project.getTags();
		}

		return Lists.newArrayList();
	}
	
	@RequestMapping(value = "/{projectKey}/branches/{branchName}/delete")
	@Secured({ Permission.PROJECT_MANAGEMENT })
	public void deleteBranch(@PathVariable String projectKey, @PathVariable String branchName) throws Throwable {
		
		Project project = projectDAO.findByKey(projectKey);
		
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		
		ProjectBranch branch = branchDAO.findByNameAndProjectId(branchName, project.getId());
		
		if (branch == null) {
			throw new ProjectBranchNotFoundException();
		}
		
		gate.dispatch(new DeleteProjectBranchCommand(branch));

	}
	
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler({ CommandExecutionException.class })
	public void handlePageNotFoundException() {
		
	}
	
}