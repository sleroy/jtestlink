package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.products.domain.ProjectCategory;

@CommandOptions
public class CreateProjectCategoryCommand {
	
	@NotNull
	private ProjectCategory category;

	public CreateProjectCategoryCommand() {
		super();
	}

	public CreateProjectCategoryCommand(final ProjectCategory category) {
		super();
		setCategory(category);
	}
	
	public ProjectCategory getCategory() {
		return category;
	}
	
	public void setCategory(final ProjectCategory category) {
		this.category = category;
	}

}
