package com.tocea.corolla.requirements.commands.handlers;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.commands.CreateRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.RequirementNode;
import com.tocea.corolla.requirements.domain.RequirementsTree;
import com.tocea.corolla.requirements.domain.TreeNode;
import com.tocea.corolla.requirements.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.requirements.exceptions.RequirementTreeNodeAlreadyExistException;
import com.tocea.corolla.requirements.exceptions.RequirementsTreeNotFoundException;

@CommandHandler
@Transactional
public class CreateRequirementTreeNodeCommandHandler implements ICommandHandler<CreateRequirementTreeNodeCommand, RequirementsTree> {
	
	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Override
	public RequirementsTree handle(@Valid CreateRequirementTreeNodeCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to retrieve the project branch");
		}
		
		String requirementId = command.getRequirementId();
		
		if (StringUtils.isEmpty(requirementId)) {
			throw new InvalidRequirementsTreeInformationException("Cannot create a requirement node without a requirement ID");
		}
		
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		Integer parentId = command.getParentId();
		
		Collection<TreeNode> nodes = Lists.newArrayList(tree.getNodes());
		
		TreeNode sameNode = getNodeByRequirementId(requirementId, nodes);
		
		if (sameNode != null) {
			throw new RequirementTreeNodeAlreadyExistException();
		}
		
		RequirementNode newNode = new RequirementNode();
		newNode.setId(getMaxNodeId(nodes)+1);
		newNode.setRequirementId(requirementId);
		newNode.setNodes((Collection<TreeNode>) new ArrayList<TreeNode>());
		
		if (parentId == null) {
			
			nodes.add(newNode);
			
		}else{
			
			TreeNode parentNode = getNodeById(parentId, nodes);
			
			if (parentNode != null) {
				Collection<TreeNode> parentNodes = Lists.newArrayList(parentNode.getNodes());
				parentNodes.add(newNode);
				parentNode.setNodes(parentNodes);
			}
			
		}
		
		tree.setNodes(nodes);
		
		requirementsTreeDAO.save(tree);
		
		return tree;
		
	}
	
	private Integer getMaxNodeId(Collection<TreeNode> nodes) {
		
		int max = 0;
		
		for(TreeNode node : nodes) {
			
			if (node.getId() > max) {				
				max = node.getId();
			}
			
			int childrenId = getMaxNodeId(node.getNodes());
			
			if (childrenId > max) {
				max = childrenId;
			}
			
		}	
		
		return max;
		
	}

	private TreeNode getNodeById(Integer id, Collection<TreeNode> nodes) {
				
		for(TreeNode node : nodes) {
			
			if (node.getId().equals(id)) {
				return node;
			}
			
			TreeNode childNode = getNodeById(id, node.getNodes());
			
			if (childNode != null) {
				return childNode;
			}
		}
		
		return null;
	}
	
	private TreeNode getNodeByRequirementId(String id, Collection<TreeNode> nodes) {
		
		for(TreeNode node : nodes) {			
			
			if (node.getClass().equals(RequirementNode.class) && ((RequirementNode)node).getRequirementId().equals(id)) {
				return node;
			}
			
			TreeNode child = getNodeByRequirementId(id, node.getNodes());
			
			if (child != null) {
				return child;
			}
		}
		
		return null;
		
	}
	
}