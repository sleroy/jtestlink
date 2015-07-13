package com.tocea.corolla.requirements.domain;

import com.tocea.corolla.trees.domain.TreeNode;

public class RequirementNode extends TreeNode {

	private String requirementId;

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	
}
