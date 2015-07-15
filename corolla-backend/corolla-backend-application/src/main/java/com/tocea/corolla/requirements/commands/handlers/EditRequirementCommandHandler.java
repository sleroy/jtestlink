package com.tocea.corolla.requirements.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.requirements.commands.EditRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.InvalidRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.MissingRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.RequirementAlreadyExistException;
import com.tocea.corolla.revisions.services.IRevisionService;

@Command
@Transactional
public class EditRequirementCommandHandler implements ICommandHandler<EditRequirementCommand, Requirement> {

	@Autowired
	private IRequirementDAO requirementDAO;
	
	@Autowired
	private IRevisionService revisionService;

	@Override
	public Requirement handle(@Valid EditRequirementCommand command) {
		
		Requirement requirement = command.getRequirement();
		
		if (requirement == null) {
			throw new MissingRequirementInformationException("No data provided to edit a requirement");
		}
		
		if (StringUtils.isEmpty(requirement.getId())) {
			throw new InvalidRequirementInformationException("No ID found");
		}
		
		if (StringUtils.isEmpty(requirement.getProjectBranchId())) {
			throw new InvalidRequirementInformationException("No branch ID found");
		}
		
		Requirement withSameKey = requirementDAO.findByKeyAndProjectBranchId(requirement.getKey(), requirement.getProjectBranchId());
		
		if (withSameKey != null && withSameKey.getId() != requirement.getId()) {
			throw new RequirementAlreadyExistException(requirement.getKey());
		}
		
		requirementDAO.save(requirement);
		
		revisionService.commit(requirement);
		
		return requirement;
		
	}
	
}