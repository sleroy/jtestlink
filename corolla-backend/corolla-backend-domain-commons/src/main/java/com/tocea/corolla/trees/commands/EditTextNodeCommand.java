package com.tocea.corolla.trees.commands;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.trees.domain.ITree;

@Command
public class EditTextNodeCommand {

	private ITree tree;
	
	private Integer nodeID;
	
	private String text;
	
	public EditTextNodeCommand() {
		super();
	}
	
	public EditTextNodeCommand(ITree tree, Integer nodeID, String text) {
		super();
		setTree(tree);
		setNodeID(nodeID);
		setText(text);
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
