package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateProjectCommand;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.exceptions.InvalidProjectInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectInformationException;
import com.tocea.corolla.products.exceptions.ProjectAlreadyExistException;

@CommandHandler
@Transactional
public class CreateProjectCommandHandler implements ICommandHandler<CreateProjectCommand, Project> {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Override
	public Project handle(@Valid CreateProjectCommand command) {
		
		Project project = command.getProject();
		
		if (project == null) {
			throw new MissingProjectInformationException("No data provided to create a project");
		}
		
		if (StringUtils.isNotEmpty(project.getId())) {
			throw new InvalidProjectInformationException("No ID expected");
		}	
		
		Project existingProject = projectDAO.findByKey(project.getKey());
		
		if (existingProject != null) {
			throw new ProjectAlreadyExistException();
		}
		
		projectDAO.save(project);
		
		return project;
		
	}
	
}
