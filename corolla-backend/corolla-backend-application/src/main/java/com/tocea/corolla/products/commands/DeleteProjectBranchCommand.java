package com.tocea.corolla.products.commands;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectBranch;

@Command
public class DeleteProjectBranchCommand {

	private ProjectBranch branch;
	
	public DeleteProjectBranchCommand() {
		super();
	}
	
	public DeleteProjectBranchCommand(ProjectBranch branch) {
		super();
		setBranch(branch);
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}
	
}
