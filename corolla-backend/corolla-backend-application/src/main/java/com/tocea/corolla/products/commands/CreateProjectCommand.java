package com.tocea.corolla.products.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.Project;

@Command
public class CreateProjectCommand {

	@NotNull
	private Project project;
	
	public CreateProjectCommand() {
		super();
	}
	
	public CreateProjectCommand(Project _project) {
		super();
		setProject(_project);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(@Valid Project project) {
		this.project = project;
	}
	
}
