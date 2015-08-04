package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.ProjectCategory;

@Command
public class CreateProjectCategoryCommand {

	@NotNull
	private ProjectCategory category;
	
	public CreateProjectCategoryCommand() {
		super();
	}
	
	public CreateProjectCategoryCommand(ProjectCategory category) {
		super();
		setCategory(category);
	}

	public ProjectCategory getCategory() {
		return category;
	}

	public void setCategory(ProjectCategory category) {
		this.category = category;
	}
	
}
