package com.tocea.corolla.requirements.trees.commands;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectBranch;

@Command
public class EditRequirementTextNodeCommand {

	private ProjectBranch branch;
	
	private Integer nodeID;
	
	private String text;
	
	public EditRequirementTextNodeCommand() {
		super();
	}

	public EditRequirementTextNodeCommand(ProjectBranch branch, Integer nodeID, String text) {
		super();
		setBranch(branch);
		setNodeID(nodeID);
		setText(text);
	}
	
	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
