package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateProjectCategoryCommand;
import com.tocea.corolla.products.dao.IProjectCategoryDAO;
import com.tocea.corolla.products.domain.ProjectCategory;
import com.tocea.corolla.products.exceptions.InvalidProjectCategoryInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectCategoryInformationException;
import com.tocea.corolla.products.exceptions.ProjectCategoryAlreadyExistException;

@CommandHandler
@Transactional
public class CreateProjectCategoryCommandHandler implements ICommandHandler<CreateProjectCategoryCommand, ProjectCategory> {

	@Autowired
	private IProjectCategoryDAO categoryDAO;
	
	@Override
	public ProjectCategory handle(@Valid CreateProjectCategoryCommand command) {
		
		ProjectCategory category = command.getCategory();
		
		if (category == null) {
			throw new MissingProjectCategoryInformationException("No category found");
		}
		
		if (StringUtils.isEmpty(category.getName())) {
			throw new MissingProjectCategoryInformationException("Cannot create a project category without a name");
		}
		
		if (StringUtils.isNotEmpty(category.getId())) {
			throw new InvalidProjectCategoryInformationException("No ID expected");
		}
		
		ProjectCategory withSameName = categoryDAO.findByName(category.getName());
		
		if (withSameName != null) {
			throw new ProjectCategoryAlreadyExistException();
		}
		
		categoryDAO.save(category);
		
		return category;
	}

}