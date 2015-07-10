package com.tocea.corolla.requirements.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectBranch;

@Command
public class CreateRequirementTreeNodeCommand {

	@NotNull
	private ProjectBranch branch;
	
	@NotEmpty
	private String requirementId;
	
	private Integer parentId;
	
	public CreateRequirementTreeNodeCommand() {
		super();
	}
	
	public CreateRequirementTreeNodeCommand(ProjectBranch branch, String requirementId, Integer parentId) {
		super();
		setBranch(branch);
		setRequirementId(requirementId);
		setParentId(parentId);
	}

	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public ProjectBranch getBranch() {
		return branch;
	}

	public void setBranch(ProjectBranch branch) {
		this.branch = branch;
	}
	
}
