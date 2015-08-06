package com.tocea.corolla.products.commands;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.portfolio.dto.ProjectNodeDTO;
import com.tocea.corolla.products.domain.Project;

@Command
public class CreateProjectCommand {

	@NotNull
	private Project project;
	
	private Integer parentNodeID;
	
	public CreateProjectCommand() {
		super();
	}
	
	public CreateProjectCommand(Project _project) {
		super();
		setProject(_project);
	}
	
	public CreateProjectCommand(Project _project, Integer parentNodeID) {
		super();
		setProject(_project);
		setParentNodeID(parentNodeID);
	}

	public Project getProject() {
		return project;
	}

	public void setProject(@Valid Project project) {
		this.project = project;
	}

	public Integer getParentNodeID() {
		return parentNodeID;
	}

	public void setParentNodeID(Integer parentNodeID) {
		this.parentNodeID = parentNodeID;
	}
	
	public void fromProjectNodeDTO(ProjectNodeDTO projectNodeDTO) {
		this.project = new Project();
		this.project.setName(projectNodeDTO.getName());
		this.project.setKey(projectNodeDTO.getKey());
		setParentNodeID(projectNodeDTO.getParentID());
	}
	
}