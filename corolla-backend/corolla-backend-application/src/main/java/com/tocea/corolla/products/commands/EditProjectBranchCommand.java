package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.ProjectBranch;

@CommandOptions
public class EditProjectBranchCommand {
	
	@NotNull
	private ProjectBranch branch;
	
	public EditProjectBranchCommand() {
		super();
	}
	
	public EditProjectBranchCommand(ProjectBranch branch) {
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
