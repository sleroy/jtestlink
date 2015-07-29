package com.tocea.corolla.trees.predicates;

import com.tocea.corolla.trees.domain.TreeNode;

public class FindNodeByIDPredicate implements ITreeNodePredicate {

	private Integer nodeID;
	
	public FindNodeByIDPredicate(final Integer nodeID) {
		this.nodeID = nodeID;
	}
	
	@Override
	public boolean test(TreeNode node) {
		return (node.getId().equals(nodeID));
	}

}
