package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.ProjectBranch;

@CommandOptions
public class MergeProjectBranchCommand {

	@NotNull
	private ProjectBranch origin;
	
	@NotNull
	private ProjectBranch destination;
	
	public MergeProjectBranchCommand() {
		super();
	}
	
	public MergeProjectBranchCommand(ProjectBranch origin, ProjectBranch destination) {
		super();
		setOrigin(origin);
		setDestination(destination);
	}

	public ProjectBranch getOrigin() {
		return origin;
	}

	public void setOrigin(ProjectBranch origin) {
		this.origin = origin;
	}

	public ProjectBranch getDestination() {
		return destination;
	}

	public void setDestination(ProjectBranch destination) {
		this.destination = destination;
	}
	
}
