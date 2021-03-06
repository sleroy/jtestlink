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
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.InvalidProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.ProjectBranchOperationForbiddenException;
import com.tocea.corolla.requirements.commands.DeleteRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;

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
	private IProjectDAO projectDAO;
	
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
		
		if (branch.getDefaultBranch()) {
			
			// The only way to delete the default branch of a project is to delete the project first
			Project project = projectDAO.findOne(branch.getProjectId());
			
			if (project != null) {
				throw new ProjectBranchOperationForbiddenException("Cannot delete the default branch");
			}
			
		}
		
		// Delete the branch
		branchDAO.delete(branch);
		
		// Delete the requirements tree of the branch
		RequirementsTree requirementsTree = requirementsTreeDAO.findByBranchId(branch.getId());
		if (requirementsTree != null) {
			requirementsTreeDAO.delete(requirementsTree);
		}
				
		Collection<Requirement> requirements = requirementDAO.findByProjectBranchId(branch.getId());
		
		// Delete all the requirements attached to this branch
		for(Requirement requirement : requirements) {			
			gate.dispatch(new DeleteRequirementCommand(requirement.getId()));			
		}
		
		return branch;
	}

}