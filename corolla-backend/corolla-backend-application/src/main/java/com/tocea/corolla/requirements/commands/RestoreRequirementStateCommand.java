package com.tocea.corolla.requirements.commands;

import com.tocea.corolla.cqrs.annotations.CommandOptions;

@CommandOptions
public class RestoreRequirementStateCommand {

	private String requirementID;
	
	private String commitID;
	
	public RestoreRequirementStateCommand() {
		super();
	}
	
	public RestoreRequirementStateCommand(String requirementID, String commitID) {
		super();
		setRequirementID(requirementID);
		setCommitID(commitID);
	}

	public String getCommitID() {
		return commitID;
	}

	public void setCommitID(String commitID) {
		this.commitID = commitID;
	}

	public String getRequirementID() {
		return requirementID;
	}

	public void setRequirementID(String requirementID) {
		this.requirementID = requirementID;
	}
	
}
