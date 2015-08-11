package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.RestoreProjectStateCommand;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.revisions.exceptions.InvalidCommitInformationException;
import com.tocea.corolla.revisions.services.IRevisionService;

@CommandHandler
@Transactional
public class RestoreProjectStateCommandHandler implements ICommandHandler<RestoreProjectStateCommand, Project> {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Override
	public Project handle(@Valid RestoreProjectStateCommand command) {
		
		Project project = command.getProject();
		String commitID = command.getCommitID();
		
		Project restoredVersion = (Project) revisionService.getSnapshot(project.getId(), Project.class, commitID);
		
		if (restoredVersion == null) {
			throw new InvalidCommitInformationException("Cannot restore the project to the given commit ID");
		}
		
		projectDAO.save(restoredVersion);
		revisionService.commit(restoredVersion);
		
		return project;
	}

}