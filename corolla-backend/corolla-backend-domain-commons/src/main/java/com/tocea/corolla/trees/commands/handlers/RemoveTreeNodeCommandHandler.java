package com.tocea.corolla.trees.commands.handlers;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.trees.commands.RemoveTreeNodeCommand;
import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
@Transactional
public class RemoveTreeNodeCommandHandler implements ICommandHandler<RemoveTreeNodeCommand, ITree> {

	@Override
	public ITree handle(@Valid RemoveTreeNodeCommand command) {
		
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
		
		TreeNode parentNode = TreeNodeUtils.getParentNodeOf(nodeID, nodes);
		
		if (parentNode == null) {
			
			nodes.remove(node);
		
		}else{
		
			parentNode.getNodes().remove(node);
			
		}
		
		tree.setNodes(nodes);
		
		return tree;
		
	}
	
}
