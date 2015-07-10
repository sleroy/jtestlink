package com.tocea.corolla.requirements.commands.handlers;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.requirements.commands.CreateRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.InvalidRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.MissingRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.RequirementAlreadyExistException;
import com.tocea.corolla.revisions.services.IRevisionService;

@CommandHandler
@Transactional
public class CreateRequirementCommandHandler implements ICommandHandler<CreateRequirementCommand, Requirement> {

	@Autowired
	private IRequirementDAO requirementDAO;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Override
	public Requirement handle(CreateRequirementCommand command) {
		
		Requirement requirement = command.getRequirement();
		
		if (requirement == null) {
			throw new MissingRequirementInformationException("No data provided to create a requirement");
		}
		
		if (StringUtils.isNotEmpty(requirement.getId())) {
			throw new InvalidRequirementInformationException("No ID expected");
		}
		
		Requirement withSameKey = requirementDAO.findByKeyAndProjectBranchId(requirement.getKey(), requirement.getProjectBranchId());
		
		if (withSameKey != null) {
			throw new RequirementAlreadyExistException(requirement.getKey());
		}
		
		requirementDAO.save(requirement);
		
		revisionService.commit(requirement);
		
		return requirement;
		
	}

}
