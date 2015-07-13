package com.tocea.corolla.trees.commands;

import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class CreateTreeNodeCommand {

	private TreeNode node;
	
	private ITree tree;
	
	private Integer parentID;
	
	public CreateTreeNodeCommand() {
		super();
	}
	
	public CreateTreeNodeCommand(ITree tree, TreeNode node, Integer parentID) {
		super();
		setTree(tree);
		setNode(node);
		setParentID(parentID);
	}

	public TreeNode getNode() {
		return node;
	}

	public void setNode(TreeNode node) {
		this.node = node;
	}

	public ITree getTree() {
		return tree;
	}

	public void setTree(ITree tree) {
		this.tree = tree;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}
	
}
