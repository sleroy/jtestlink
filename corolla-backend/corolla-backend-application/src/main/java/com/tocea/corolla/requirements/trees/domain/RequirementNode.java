package com.tocea.corolla.requirements.trees.domain;

import com.tocea.corolla.trees.domain.TreeNode;

public class RequirementNode extends TreeNode implements IRequirementTreeNode {

	private String requirementId;

	public RequirementNode() {
		super();
	}
	
	public RequirementNode(String requirementId) {
		super();
		setRequirementId(requirementId);
	}
	
	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	
	@Override
	public TreeNode clone() {		
		return this.clone(new RequirementNode(this.requirementId));		
	}
	
}
