package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.EditProjectCommand;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.exceptions.InvalidProjectInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectInformationException;
import com.tocea.corolla.products.exceptions.ProjectAlreadyExistException;
import com.tocea.corolla.revisions.services.IRevisionService;

@CommandHandler
@Transactional
public class EditProjectCommandHandler implements ICommandHandler<EditProjectCommand, Project> {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Override
	public Project handle(EditProjectCommand command) {
		
		Project project = command.getProject();
		
		if (project == null) {
			throw new MissingProjectInformationException("No data provided to edit a project");
		}
		
		if (StringUtils.isEmpty(project.getId())) {
			throw new InvalidProjectInformationException("No ID found");
		}
		
		Project existingProject = projectDAO.findByKey(project.getKey());
		
		if (existingProject != null && !existingProject.getId().equals(project.getId())) {
			throw new ProjectAlreadyExistException();
		}
		
		project = projectDAO.save(project);
		
		revisionService.commit(project);
		
		return project;
		
	}
	
}
