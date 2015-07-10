package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateRequirementTypeCommand;
import com.tocea.corolla.products.dao.IRequirementTypeDAO;
import com.tocea.corolla.products.domain.RequirementType;
import com.tocea.corolla.products.exceptions.InvalidRequirementTypeInformationException;
import com.tocea.corolla.products.exceptions.MissingRequirementTypeInformationException;
import com.tocea.corolla.products.exceptions.RequirementTypeAlreadyExistException;

@CommandHandler
@Transactional
public class CreateRequirementTypeCommandHandler implements ICommandHandler<CreateRequirementTypeCommand, RequirementType> {

	@Autowired
	private IRequirementTypeDAO typeDAO;
	
	@Override
	public RequirementType handle(@Valid CreateRequirementTypeCommand command) {
		
		RequirementType type = command.getType();
		
		if (type == null) {
			throw new MissingRequirementTypeInformationException("No data found for creating a requirement type");
		}
		
		if (StringUtils.isNotEmpty(type.getId())) {
			throw new InvalidRequirementTypeInformationException("No ID expected");
		}
		
		RequirementType withSameKey = typeDAO.findByKey(type.getKey());
		
		if (withSameKey != null) {
			throw new RequirementTypeAlreadyExistException();
		}
		
		type.setActive(true);
		
		typeDAO.save(type);
		
		return type;
		
	}

}
