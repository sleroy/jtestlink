package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.ProjectStatus;

@CommandOptions
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
