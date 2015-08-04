package com.tocea.corolla.trees.domain;

import java.util.ArrayList;

public class FolderNode extends TreeNode {

	private String text;
	
	private String typeID;
	
	public FolderNode() {
		super();
	}
	
	public FolderNode(String text, String typeID) {
		super();
		setText(text);
		setNodes(new ArrayList<TreeNode>());
		setTypeID(typeID);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	
	@Override
	public TreeNode clone() {
		
		return super.clone(new FolderNode(this.text, this.typeID));

	}
	
}