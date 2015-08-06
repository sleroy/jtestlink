package com.tocea.corolla.requirements.trees.commands;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.ProjectBranch;

@CommandOptions
public class RemoveRequirementTreeNodeCommand {

	private Integer nodeID;
	
	private ProjectBranch branch;
	
	public RemoveRequirementTreeNodeCommand() {
		super();
	}
	
	public RemoveRequirementTreeNodeCommand(ProjectBranch branch, Integer nodeID) {
		super();
		setBranch(branch);
		setNodeID(nodeID);
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}
	
}
