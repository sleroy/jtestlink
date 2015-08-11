package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.Project;

@CommandOptions
public class RestoreProjectStateCommand {

	@NotNull
	private Project project;
	
	@NotEmpty
	private String commitID;

	public RestoreProjectStateCommand(Project project, String commitID) {
		super();
		setCommitID(commitID);
		setProject(project);
	}

	public String getCommitID() {
		return commitID;
	}

	public void setCommitID(String commitID) {
		this.commitID = commitID;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}