package com.tocea.corolla.requirements.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.requirements.commands.DeleteRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.MissingRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.RequirementNotFoundException;

@CommandHandler
@Transactional
public class DeleteRequirementCommandHandler implements ICommandHandler<DeleteRequirementCommand, Requirement> {

	@Autowired
	private IRequirementDAO requirementDAO;
	
	@Override
	public Requirement handle(@Valid DeleteRequirementCommand command) {
		
		String requirementID = command.getRequirementID();
		
		if (StringUtils.isEmpty(requirementID)) {
			throw new MissingRequirementInformationException("No requirement ID found");
		}
		
		Requirement requirement = requirementDAO.findOne(requirementID);
		
		if (requirement == null) {
			throw new RequirementNotFoundException();
		}
		
		requirementDAO.delete(requirement);
		
		return requirement;
		
	}

}
