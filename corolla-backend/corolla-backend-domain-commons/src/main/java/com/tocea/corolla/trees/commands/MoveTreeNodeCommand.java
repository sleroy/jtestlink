package com.tocea.corolla.trees.commands;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.trees.domain.ITree;

@Command
public class MoveTreeNodeCommand {

	private ITree tree;
	
	private Integer nodeID;
	
	private Integer newParentID;

	public MoveTreeNodeCommand() {
		super();
	}
	
	public MoveTreeNodeCommand(ITree tree, Integer nodeID, Integer newParentID) {
		super();
		setTree(tree);
		setNodeID(nodeID);
		setNewParentID(newParentID);
	}
	
	public ITree getTree() {
		return tree;
	}

	public void setTree(ITree tree) {
		this.tree = tree;
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}

	public Integer getNewParentID() {
		return newParentID;
	}

	public void setNewParentID(Integer newParentID) {
		this.newParentID = newParentID;
	}
	
}
