package com.tocea.corolla.trees.predicates;

import java.util.function.Predicate;

import com.tocea.corolla.trees.domain.TreeNode;

public class FindNodeByIDPredicate implements Predicate<TreeNode> {

	private Integer nodeID;
	
	public FindNodeByIDPredicate(final Integer nodeID) {
		this.nodeID = nodeID;
	}
	
	@Override
	public boolean test(TreeNode node) {
		return (node.getId().equals(nodeID));
	}

}
