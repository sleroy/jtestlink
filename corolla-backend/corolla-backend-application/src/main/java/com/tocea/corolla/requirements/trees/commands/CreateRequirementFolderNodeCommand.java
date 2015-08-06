package com.tocea.corolla.requirements.trees.commands;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.trees.domain.FolderNodeType;

@CommandOptions
public class CreateRequirementFolderNodeCommand {

	@NotNull
	private ProjectBranch branch;
	
	private Integer parentID;
	
	private String typeID;
	
	@NotEmpty
	@Size(min=1, max=100)
	private String text;
	
	public CreateRequirementFolderNodeCommand() {
		super();
	}
	
	public CreateRequirementFolderNodeCommand(ProjectBranch branch, Integer parentID, String text, String typeID) {
		super();
		setBranch(branch);
		setParentID(parentID);
		setText(text);
		setTypeID(typeID);
	}
	
	public CreateRequirementFolderNodeCommand(ProjectBranch branch, Integer parentID, String text, FolderNodeType type) {
		this(branch, parentID, text, type.getId());
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	
}
