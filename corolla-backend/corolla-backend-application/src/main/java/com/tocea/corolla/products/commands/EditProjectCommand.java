package com.tocea.corolla.products.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.Project;

@CommandOptions
public class EditProjectCommand {

	@NotNull
	private Project project;
	
	public EditProjectCommand() {
		super();
	}
	
	public EditProjectCommand(Project _project) {
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
