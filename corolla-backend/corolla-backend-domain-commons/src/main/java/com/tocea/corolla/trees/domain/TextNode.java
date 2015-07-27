package com.tocea.corolla.trees.domain;

import java.util.ArrayList;

public class TextNode extends TreeNode {

	private String text;
	
	public TextNode() {
		super();
	}
	
	public TextNode(String text) {
		super();
		setText(text);
		setNodes(new ArrayList<TreeNode>());
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public TreeNode clone() {
		
		return super.clone(new TextNode(this.text));

	}
	
}
