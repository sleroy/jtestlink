package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateProjectStatusCommand;
import com.tocea.corolla.products.dao.IProjectStatusDAO;
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.products.exceptions.InvalidProjectStatusInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectStatusInformationException;
import com.tocea.corolla.products.exceptions.ProjectStatusAlreadyExistException;

@CommandHandler
@Transactional
public class CreateProjectStatusCommandHandler implements ICommandHandler<CreateProjectStatusCommand, ProjectStatus> {

	@Autowired
	private IProjectStatusDAO statusDAO;
	
	@Override
	public ProjectStatus handle(@Valid CreateProjectStatusCommand command) {
		
		ProjectStatus status = command.getStatus();
		
		if (status == null) {
			throw new MissingProjectStatusInformationException("No data provided to create a project status");
		}
		
		if (StringUtils.isNotEmpty(status.getId())) {
			throw new InvalidProjectStatusInformationException("No ID expected");
		}
		
		ProjectStatus withSameName = statusDAO.findByName(status.getName());
		
		if (withSameName != null) {
			throw new ProjectStatusAlreadyExistException();
		}
		
		statusDAO.save(status);
		
		return status;

	}
	
}
