package com.tocea.corolla.requirements.trees.commands;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectBranch;

@Command
public class EditRequirementFolderNodeCommand {

	private ProjectBranch branch;
	
	private Integer nodeID;
	
	@NotBlank
	@Size(min = 1, max = 100)
	private String text;
	
	public EditRequirementFolderNodeCommand() {
		super();
	}

	public EditRequirementFolderNodeCommand(ProjectBranch branch, Integer nodeID, String text) {
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
