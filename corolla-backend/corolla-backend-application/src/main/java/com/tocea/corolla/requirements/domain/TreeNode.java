package com.tocea.corolla.requirements.domain;

import java.util.Collection;

public class TreeNode {

	private Integer id;
	
	private Collection<TreeNode> nodes;

	public Collection<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<TreeNode> nodes) {
		this.nodes = nodes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
