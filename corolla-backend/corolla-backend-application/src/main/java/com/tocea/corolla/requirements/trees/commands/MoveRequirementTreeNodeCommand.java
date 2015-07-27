package com.tocea.corolla.requirements.trees.commands;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectBranch;

@Command
public class MoveRequirementTreeNodeCommand {

	private ProjectBranch branch;
	
	private Integer nodeId;
	
	private Integer parentId;

	public MoveRequirementTreeNodeCommand() {
		super();
	}
	
	public MoveRequirementTreeNodeCommand(ProjectBranch branch, Integer nodeId, Integer parentId) {
		super();
		setBranch(branch);
		setNodeId(nodeId);
		setParentId(parentId);
	}
	
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}
	
}
