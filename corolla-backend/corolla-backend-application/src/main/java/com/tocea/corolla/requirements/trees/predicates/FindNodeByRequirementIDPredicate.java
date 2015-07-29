package com.tocea.corolla.requirements.trees.predicates;

import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.utils.RequirementsTreeUtils;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.predicates.ITreeNodePredicate;

public class FindNodeByRequirementIDPredicate implements ITreeNodePredicate {

	private String requirementID;
	
	public FindNodeByRequirementIDPredicate(final String requirementID) {
		this.requirementID = requirementID;
	}
	
	@Override
	public boolean test(TreeNode node) {
		return (RequirementsTreeUtils.isRequirementNode(node) && ((RequirementNode) node).getRequirementId().equals(requirementID));
	}

}
