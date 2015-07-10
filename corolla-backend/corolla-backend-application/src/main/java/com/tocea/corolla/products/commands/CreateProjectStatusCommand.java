package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectStatus;

@Command
public class CreateProjectStatusCommand {

	@NotNull
	private ProjectStatus status;
	
	public CreateProjectStatusCommand() {
		super();
	}
	
	public CreateProjectStatusCommand(ProjectStatus _status) {
		setStatus(_status);
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}
	
}
