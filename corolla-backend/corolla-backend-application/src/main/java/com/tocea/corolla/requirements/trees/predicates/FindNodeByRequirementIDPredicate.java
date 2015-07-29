package com.tocea.corolla.requirements.trees.predicates;

import java.util.function.Predicate;

import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.utils.RequirementsTreeUtils;
import com.tocea.corolla.trees.domain.TreeNode;

public class FindNodeByRequirementIDPredicate implements Predicate<TreeNode> {

	private String requirementID;
	
	public FindNodeByRequirementIDPredicate(final String requirementID) {
		this.requirementID = requirementID;
	}
	
	@Override
	public boolean test(TreeNode node) {
		return (RequirementsTreeUtils.isRequirementNode(node) && ((RequirementNode) node).getRequirementId().equals(requirementID));
	}

}
