package com.tocea.corolla.products.commands;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;

@CommandOptions
public class CreateProjectBranchCommand {

	private ProjectBranch branch;
	private ProjectBranch originBranch;
	
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
	
	public CreateProjectBranchCommand(String name, ProjectBranch originBranch) {
		super();
		ProjectBranch branch = new ProjectBranch();
		branch.setName(name);
		branch.setProjectId(originBranch.getProjectId());
		setBranch(branch);
		setOriginBranch(originBranch);
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}

	public ProjectBranch getOriginBranch() {
		return originBranch;
	}

	public void setOriginBranch(ProjectBranch originBranch) {
		this.originBranch = originBranch;
	}
	
}
