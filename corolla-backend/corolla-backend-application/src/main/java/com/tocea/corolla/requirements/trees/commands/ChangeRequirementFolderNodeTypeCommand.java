package com.tocea.corolla.requirements.trees.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.ProjectBranch;

@CommandOptions
public class ChangeRequirementFolderNodeTypeCommand {

	@NotNull
	private ProjectBranch branch;
	
	@NotNull
	private Integer nodeID;
	
	@NotBlank
	private String typeID;
	
	public ChangeRequirementFolderNodeTypeCommand() {
		super();
	}
	
	public ChangeRequirementFolderNodeTypeCommand(ProjectBranch branch, Integer nodeID, String typeID) {
		super();
		setBranch(branch);
		setNodeID(nodeID);
		setTypeID(typeID);
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}
	
}
