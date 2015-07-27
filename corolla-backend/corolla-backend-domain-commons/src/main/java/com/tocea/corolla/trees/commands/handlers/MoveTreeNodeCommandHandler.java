package com.tocea.corolla.trees.commands.handlers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand;
import com.tocea.corolla.trees.commands.MoveTreeNodeCommand;
import com.tocea.corolla.trees.commands.RemoveTreeNodeCommand;
import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
public class MoveTreeNodeCommandHandler implements ICommandHandler<MoveTreeNodeCommand, ITree> {

	@Autowired
	private Gate gate;
	
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
			
			tree = gate.dispatch(new RemoveTreeNodeCommand(tree, nodeID));
			tree = gate.dispatch(new CreateTreeNodeCommand(tree, node, newParentId));
			
		}else{
			
			TreeNode newParentNode = TreeNodeUtils.getNodeById(newParentId, nodes);
			
			if (newParentNode == null) {
				throw new InvalidTreeNodeInformationException("The given parent node ID does not match any node of this tree");
			}
			
			if (TreeNodeUtils.hasNodeWithId(newParentId, node, true)) {
				throw new InvalidTreeNodeInformationException("Cannot move a tree node inside one of its own children");
			}
			
			tree = gate.dispatch(new RemoveTreeNodeCommand(tree, nodeID));
			tree = gate.dispatch(new CreateTreeNodeCommand(tree, node, newParentId));
			
		}
		
		return tree;
	}
	
}