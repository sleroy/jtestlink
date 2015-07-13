package com.tocea.corolla.trees.commands.handlers;

import java.util.Collection;

import javax.validation.Valid;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand;
import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
public class CreateTreeNodeCommandHandler implements ICommandHandler<CreateTreeNodeCommand, ITree> {

	@Override
	public ITree handle(@Valid CreateTreeNodeCommand command) {
		
		ITree tree = command.getTree();
		
		if (tree == null) {
			throw new MissingTreeInformationException("No tree found");
		}
		
		TreeNode newNode = command.getNode();
		
		if (newNode == null) {
			throw new MissingTreeNodeInformationException("The tree node is null");
		}
		
		Integer parentId = command.getParentID();
		
		Collection<TreeNode> nodes = Lists.newArrayList(tree.getNodes());
		
		newNode.setId(TreeNodeUtils.getMaxNodeId(nodes)+1);
		
		if (parentId == null) {
			
			nodes.add(newNode);
			
		}else{
			
			TreeNode parentNode = TreeNodeUtils.getNodeById(parentId, nodes);
			
			if (parentNode != null) {
				Collection<TreeNode> parentNodes = Lists.newArrayList(parentNode.getNodes());
				parentNodes.add(newNode);
				parentNode.setNodes(parentNodes);
			}
			
		}
		
		tree.setNodes(nodes);
		
		return tree;
		
	}

}
