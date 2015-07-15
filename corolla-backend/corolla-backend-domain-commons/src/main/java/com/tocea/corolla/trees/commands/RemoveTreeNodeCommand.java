package com.tocea.corolla.trees.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.trees.domain.ITree;

@Command
public class RemoveTreeNodeCommand {

	@NotNull
	private Integer nodeID;
	
	@NotNull
	private ITree tree;
	
	public RemoveTreeNodeCommand() {
		super();
	}
	
	public RemoveTreeNodeCommand(ITree tree, Integer nodeID) {
		super();
		setTree(tree);
		setNodeID(nodeID);
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}

	public ITree getTree() {
		return tree;
	}

	public void setTree(ITree tree) {
		this.tree = tree;
	}
	
}
