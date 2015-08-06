package com.tocea.corolla.products.commands;

import org.hibernate.validator.constraints.NotEmpty;

import com.tocea.corolla.cqrs.annotations.CommandOptions;

@CommandOptions
public class DeleteProjectCommand {

	@NotEmpty
	private String projectID;
	
	public DeleteProjectCommand() {
		
	}
	
	public DeleteProjectCommand(String _id) {
		setProjectID(_id);
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
}
