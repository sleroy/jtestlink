package com.tocea.corolla.requirements.commands.handlers;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException;
import com.tocea.corolla.requirements.commands.CreateRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.InvalidRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.MissingRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.RequirementAlreadyExistException;
import com.tocea.corolla.requirements.trees.commands.CreateRequirementTreeNodeCommand;
import com.tocea.corolla.revisions.services.IRevisionService;

@CommandHandler
@Transactional
public class CreateRequirementCommandHandler implements ICommandHandler<CreateRequirementCommand, Requirement> {

	@Autowired
	private IRequirementDAO requirementDAO;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired
	private Gate gate;
	
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
		
		ProjectBranch branch = branchDAO.findOne(requirement.getProjectBranchId());
		
		if (branch == null) {
			throw new ProjectBranchNotFoundException();
		}
		
		requirementDAO.save(requirement);
		
		revisionService.commit(requirement);
		
		// Insert a node attached to this requirement in the requirements tree
		Integer parentNodeID = command.getParentNodeID();	
		gate.dispatch(new CreateRequirementTreeNodeCommand(branch, requirement.getId(), parentNodeID));
		
		return requirement;
		
	}

}
