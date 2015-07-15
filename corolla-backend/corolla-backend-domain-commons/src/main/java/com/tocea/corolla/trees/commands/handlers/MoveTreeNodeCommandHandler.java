package com.tocea.corolla.trees.commands.handlers;

import java.util.Collection;

import javax.validation.Valid;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.trees.commands.MoveTreeNodeCommand;
import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
public class MoveTreeNodeCommandHandler implements ICommandHandler<MoveTreeNodeCommand, ITree> {

	@Override
	public ITree handle(@Valid MoveTreeNodeCommand command) {
		
		ITree tree = command.getTree();
		
		if (tree == null) {
			throw new MissingTreeInformationException("No tree found");
		}
		
		Integer nodeID = command.getNodeID();
		
		if (nodeID == null) {
			throw new MissingTreeNodeInformationException("No ID found");
		}
		
		Collection<TreeNode> nodes = Lists.newArrayList(tree.getNodes());
		
		TreeNode node = TreeNodeUtils.getNodeById(nodeID, nodes);
		
		if (node == null) {
			throw new InvalidTreeNodeInformationException("The given node ID does not match any node of this tree");
		}
		
		Integer newParentId = command.getNewParentID();
		
		if (newParentId == null) {
			
			removeFromTree(node, nodes);	
			nodes.add(node);
			
		}else{
			
			TreeNode newParentNode = TreeNodeUtils.getNodeById(newParentId, nodes);
			
			if (newParentNode == null) {
				throw new InvalidTreeNodeInformationException("The given parent node ID does not match any node of this tree");
			}
			
			if (TreeNodeUtils.hasNodeWithId(newParentId, node, true)) {
				throw new InvalidTreeNodeInformationException("Cannot move a tree node inside one of its own children");
			}
			
			removeFromTree(node, nodes);
			newParentNode.getNodes().add(node);
			
		}
		
		tree.setNodes(nodes);
		
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