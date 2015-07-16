package com.tocea.corolla.trees.domain;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

public class TreeNode implements Cloneable {

	private Integer id;
	
	private Collection<TreeNode> nodes;
	
	public TreeNode() {
		
	}

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
	
	@Override
	public TreeNode clone() {
		
		return clone(new TreeNode());
			
	}
	
	protected TreeNode clone(TreeNode clone) {
		
		clone.setId(this.id);
		
		List<TreeNode> nodesCloned = Lists.newArrayList();
		
		for(TreeNode node : this.nodes) {			
			nodesCloned.add(node.clone());			
		}
		
		clone.setNodes(nodesCloned);
		
		return clone;	
		
	}
	
}
