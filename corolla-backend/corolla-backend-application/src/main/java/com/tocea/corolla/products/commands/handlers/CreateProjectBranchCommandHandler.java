package com.tocea.corolla.products.commands.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateProjectBranchCommand;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.InvalidProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.ProjectBranchAlreadyExistException;
import com.tocea.corolla.requirements.commands.CreateRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.requirements.trees.utils.RequirementsTreeUtils;
import com.tocea.corolla.requirements.utils.RequirementUtils;
import com.tocea.corolla.trees.domain.TreeNode;

@CommandHandler
@Transactional
public class CreateProjectBranchCommandHandler implements ICommandHandler<CreateProjectBranchCommand, ProjectBranch> {

	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired 
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private IRequirementDAO requirementDAO;
	
	@Autowired
	private Gate gate;
	
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
		
		Collection<ProjectBranch> otherBranches = branchDAO.findByProjectId(branch.getProjectId());
		
		if (otherBranches.isEmpty()) {
			branch.setDefaultBranch(true);
		}
		
		branchDAO.save(branch);
		
		// Create requirements tree
		RequirementsTree requirementsTree = new RequirementsTree();
		requirementsTree.setBranchId(branch.getId());
		requirementsTree.setNodes(new ArrayList<TreeNode>());
		
		requirementsTreeDAO.save(requirementsTree);
		
		ProjectBranch originBranch = command.getOriginBranch();
		
		if (originBranch != null) {			
			cloneProjectBranch(originBranch, branch, requirementsTree);			
		}
		
		return branch;
		
	}
	
	private void cloneProjectBranch(ProjectBranch originBranch, ProjectBranch newBranch, RequirementsTree newRequirementsTree) {
		
		HashMap<String, String> mapIDs = Maps.newHashMap();
		
		Collection<Requirement> originRequirements = requirementDAO.findByProjectBranchId(originBranch.getId());
		
		if (originRequirements != null) {
		
			for(Requirement requirement : originRequirements) {

				Requirement clone = RequirementUtils.clone(requirement, newBranch.getId());
				
				gate.dispatch(new CreateRequirementCommand(clone));
				
				mapIDs.put(requirement.getId(), clone.getId());
				
			}
		
		}
		
		RequirementsTree originTree = requirementsTreeDAO.findByBranchId(originBranch.getId());
		
		if (originTree == null)  {
			throw new RequirementsTreeNotFoundException(originBranch);
		}
		
		for(TreeNode node : originTree.getNodes()) {
			
			newRequirementsTree.getNodes().add(node.clone());
			
		}
		
		for(RequirementNode node : RequirementsTreeUtils.getRequirementsNodes(newRequirementsTree.getNodes())) {
			
			node.setRequirementId(mapIDs.get(node.getRequirementId()));
			
		}
		
		requirementsTreeDAO.save(newRequirementsTree);
		
	}
	
}