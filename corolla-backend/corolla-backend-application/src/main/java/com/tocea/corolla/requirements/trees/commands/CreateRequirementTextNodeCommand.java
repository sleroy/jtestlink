package com.tocea.corolla.requirements.trees.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectBranch;

@Command
public class CreateRequirementTextNodeCommand {

	@NotNull
	private ProjectBranch branch;
	
	private Integer parentID;
	
	@NotEmpty
	private String text;
	
	public CreateRequirementTextNodeCommand() {
		super();
	}
	
	public CreateRequirementTextNodeCommand(ProjectBranch branch, Integer parentID, String text) {
		super();
		setBranch(branch);
		setParentID(parentID);
		setText(text);
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
	
}
