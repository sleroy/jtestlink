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

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.DeleteProjectBranchCommand;
import com.tocea.corolla.products.commands.DeleteProjectCommand;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectInformationException;
import com.tocea.corolla.products.exceptions.ProjectNotFoundException;
import com.tocea.corolla.revisions.services.IRevisionService;

@CommandHandler
@Transactional
public class DeleteProjectCommandHandler implements ICommandHandler<DeleteProjectCommand, Project> {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Autowired
	private Gate gate;
	
	@Override
	public Project handle(@Valid DeleteProjectCommand command) {
		
		String id = command.getProjectID();
		
		if (StringUtils.isEmpty(id)) {
			throw new MissingProjectInformationException("No ID found");
		}
		
		Project project = projectDAO.findOne(id);
		
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		
		// Delete the project from the database
		projectDAO.delete(project);
		
		// Invoke the commands to delete the project branches associated to this project
		Collection<ProjectBranch> branches = branchDAO.findByProjectId(project.getId());		
		for(ProjectBranch branch : branches) {
			gate.dispatch(new DeleteProjectBranchCommand(branch));
		}
		
		return project;
			
	}
	
}
