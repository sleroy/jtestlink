package com.tocea.corolla.trees.domain;

import java.util.Collection;

public interface ITree {

	public Collection<TreeNode> getNodes();
	
	public void setNodes(Collection<TreeNode> nodes);
	
}
