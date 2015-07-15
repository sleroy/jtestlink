package com.tocea.corolla.requirements.commands;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectBranch;

@Command
public class RemoveRequirementTreeNodeCommand {

	private String requirementID;
	
	private ProjectBranch branch;
	
	public RemoveRequirementTreeNodeCommand() {
		super();
	}
	
	public RemoveRequirementTreeNodeCommand(ProjectBranch branch, String requirementID) {
		super();
		setBranch(branch);
		setRequirementID(requirementID);
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}

	public String getRequirementID() {
		return requirementID;
	}

	public void setRequirementID(String requirementID) {
		this.requirementID = requirementID;
	}
	
}
