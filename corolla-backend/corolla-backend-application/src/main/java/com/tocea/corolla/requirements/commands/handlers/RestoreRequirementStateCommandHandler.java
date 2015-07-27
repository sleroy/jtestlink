package com.tocea.corolla.requirements.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.requirements.commands.EditRequirementCommand;
import com.tocea.corolla.requirements.commands.RestoreRequirementStateCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.MissingRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.RequirementNotFoundException;
import com.tocea.corolla.revisions.exceptions.MissingCommitInformationException;
import com.tocea.corolla.revisions.services.IRevisionService;

@CommandHandler
@Transactional
public class RestoreRequirementStateCommandHandler implements ICommandHandler<RestoreRequirementStateCommand, Requirement> {

	@Autowired
	private IRequirementDAO requirementDAO;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Autowired
	private Gate gate;
	
	@Override
	public Requirement handle(@Valid RestoreRequirementStateCommand command) {
		
		String requirementID = command.getRequirementID();
		
		if (StringUtils.isEmpty(requirementID)) {
			throw new MissingRequirementInformationException("No requirement ID found");
		}
		
		String commitID = command.getCommitID();
		
		if (StringUtils.isEmpty(commitID)) {
			throw new MissingCommitInformationException("No commit ID found");
		}
		
		Requirement requirement = requirementDAO.findOne(requirementID);
		
		if (requirement == null) {
			throw new RequirementNotFoundException();
		}
		
		Requirement restored = (Requirement) revisionService.getSnapshot(requirementID, Requirement.class, commitID);
		
		this.gate.dispatch(new EditRequirementCommand(restored));
		
		return restored;
		
	}
	
}