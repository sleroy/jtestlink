package com.tocea.corolla.products.commands.handlers;

import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateProjectBranchCommand;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.InvalidProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.ProjectBranchAlreadyExistException;
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.RequirementsTree;
import com.tocea.corolla.trees.domain.TreeNode;

@CommandHandler
@Transactional
public class CreateProjectBranchCommandHandler implements ICommandHandler<CreateProjectBranchCommand, ProjectBranch> {

	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired 
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Override
	public ProjectBranch handle(@Valid CreateProjectBranchCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to create a project branch");
		}
		
		if (StringUtils.isNotEmpty(branch.getId())) {
			throw new InvalidProjectBranchInformationException("No ID expected");
		}
		
		ProjectBranch withSameName = branchDAO.findByNameAndProjectId(branch.getName(), branch.getProjectId());
		
		if (withSameName != null) {
			throw new ProjectBranchAlreadyExistException();
		}
		
		branchDAO.save(branch);
		
		// Create requirements tree
		RequirementsTree requirementsTree = new RequirementsTree();
		requirementsTree.setBranchId(branch.getId());
		requirementsTree.setNodes(new ArrayList<TreeNode>());
		
		requirementsTreeDAO.save(requirementsTree);
		
		return branch;
		
	}
	
}
