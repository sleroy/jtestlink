package com.tocea.corolla.requirements.trees.commands.handlers;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.commands.MoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
@Transactional
public class MoveRequirementTreeNodeCommandHandler implements ICommandHandler<MoveRequirementTreeNodeCommand, RequirementsTree> {

	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Override
	public RequirementsTree handle(@Valid MoveRequirementTreeNodeCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to retrieve the project branch");
		}
		
		Integer nodeId = command.getNodeId();
		
		if (nodeId == null) {
			throw new InvalidRequirementsTreeInformationException("Node ID expected");
		}
		
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		Collection<TreeNode> nodes = Lists.newArrayList(tree.getNodes());
		
		TreeNode node = TreeNodeUtils.getNodeById(nodeId, nodes);
		
		if (node == null) {
			throw new InvalidRequirementsTreeInformationException("The given node ID does not match any node of this tree");
		}
		
		Integer newParentId = command.getParentId();
		
		if (newParentId == null) {
			
			removeFromTree(node, nodes);	
			nodes.add(node);
			
		}else{
			
			TreeNode newParentNode = TreeNodeUtils.getNodeById(newParentId, nodes);
			
			if (newParentNode == null) {
				throw new InvalidRequirementsTreeInformationException("The given parent node ID does not match any node of this tree");
			}
			
			if (TreeNodeUtils.hasNodeWithId(newParentId, node, true)) {
				throw new InvalidRequirementsTreeInformationException("Cannot move a tree node inside one of its own children");
			}
			
			removeFromTree(node, nodes);
			newParentNode.getNodes().add(node);
			
		}
		
		tree.setNodes(nodes);
		
		requirementsTreeDAO.save(tree);
		
		return tree;
		
	}
	
	private void removeFromTree(TreeNode node, Collection<TreeNode> nodes) {
		
		TreeNode currentParentNode = TreeNodeUtils.getParentNodeOf(node.getId(), nodes);
		
		if (currentParentNode == null) {
			nodes.remove(node);
		}else{
			currentParentNode.getNodes().remove(node);
		}
		
	}

}