package com.tocea.corolla.products.commands;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;

@Command
public class CreateProjectBranchCommand {

	private ProjectBranch branch;
	
	public CreateProjectBranchCommand() {
		super();
	}
	
	public CreateProjectBranchCommand(ProjectBranch _branch) {
		super();
		setBranch(_branch);
	}
	
	public CreateProjectBranchCommand(String name, Project project) {
		super();
		ProjectBranch branch = new ProjectBranch();
		branch.setName(name);
		branch.setProjectId(project.getId());
		setBranch(branch);
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}
	
}
