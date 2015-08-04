package com.tocea.corolla.requirements.trees.commands.handlers;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.commands.DeleteRequirementCommand;
import com.tocea.corolla.requirements.trees.commands.RemoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementTreeNodeNotFoundException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.requirements.trees.utils.RequirementsTreeUtils;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
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
		
		Integer nodeID = command.getNodeID();
		
		if (nodeID == null) {
			throw new MissingTreeNodeInformationException("No node ID found");
		}
		
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		TreeNode node = treeManagementService.findNode(tree, new FindNodeByIDPredicate(nodeID));
		
		if (node == null) {
			throw new RequirementTreeNodeNotFoundException();
		}
			
		cleanRequirements(node);
		
		treeManagementService.removeNode(tree, node.getId());
		
		requirementsTreeDAO.save(tree);
		
		return tree;
	}
	
	private void cleanRequirements(TreeNode node) {
		
		if (RequirementsTreeUtils.isRequirementNode(node)) {
			String requirementID = ((RequirementNode) node).getRequirementId();
			gate.dispatch(new DeleteRequirementCommand(requirementID));
		}
		
		Collection<String> ids = RequirementsTreeUtils.getRequirementsIDs(node.getNodes());
		
		for(String ID : ids) {
			gate.dispatch(new DeleteRequirementCommand(ID));
		}
		
	}
	
}