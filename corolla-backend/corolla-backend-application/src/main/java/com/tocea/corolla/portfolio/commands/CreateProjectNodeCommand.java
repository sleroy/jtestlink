package com.tocea.corolla.portfolio.commands;

import com.tocea.corolla.cqrs.annotations.CommandOptions;

@CommandOptions
public class CreateProjectNodeCommand {

	private String projectID;
	
	private Integer parentID;
	
	public CreateProjectNodeCommand() {
		super();
	}
	
	public CreateProjectNodeCommand(String projectID, Integer parentID) {
		super();
		setProjectID(projectID);
		setParentID(parentID);
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}
	
}