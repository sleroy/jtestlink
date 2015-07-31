package com.tocea.corolla.requirements.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.commands.DeleteRequirementCommand;
import com.tocea.corolla.requirements.exceptions.MissingRequirementInformationException;
import com.tocea.corolla.requirements.trees.commands.RemoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementTreeNodeNotFoundException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.requirements.trees.predicates.FindNodeByRequirementIDPredicate;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.services.ITreeManagementService;

@CommandHandler
@Transactional
public class RemoveRequirementTreeNodeCommandHandler implements ICommandHandler<RemoveRequirementTreeNodeCommand, RequirementsTree> {

	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public RequirementsTree handle(@Valid RemoveRequirementTreeNodeCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No project branch found");
		}
		
		String requirementID = command.getRequirementID();
		
		if (StringUtils.isEmpty(requirementID)) {
			throw new MissingRequirementInformationException("No requirement ID found");
		}
		
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		TreeNode node = treeManagementService.findNode(tree, new FindNodeByRequirementIDPredicate(requirementID));
		
		if (node == null) {
			throw new RequirementTreeNodeNotFoundException();
		}
		
		gate.dispatch(new DeleteRequirementCommand(requirementID));
		
		treeManagementService.removeNode(tree, node.getId());
		
		requirementsTreeDAO.save(tree);
		
		return tree;
	}
	
}