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
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.InvalidProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.commands.DeleteRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;

@CommandHandler
@Transactional
public class DeleteProjectBranchCommandHandler implements ICommandHandler<DeleteProjectBranchCommand, ProjectBranch> {

	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private IRequirementDAO requirementDAO;
	
	@Autowired
	private Gate gate;
	
	@Override
	public ProjectBranch handle(@Valid DeleteProjectBranchCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to delete a branch");
		}
		
		if (StringUtils.isEmpty(branch.getId())) {
			throw new InvalidProjectBranchInformationException("No ID found on the given branch");
		}
		
		// Delete the branch
		branchDAO.delete(branch);
		
		// Delete the requirements tree of the branch
		requirementsTreeDAO.deleteByBranchId(branch.getId());
				
		Collection<Requirement> requirements = requirementDAO.findByProjectBranchId(branch.getId());
		
		// Delete all the requirements attached to this branch
		for(Requirement requirement : requirements) {			
			gate.dispatch(new DeleteRequirementCommand(requirement.getId()));			
		}
		
		return branch;
	}

}