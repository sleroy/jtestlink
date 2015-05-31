/**
 *
 */
package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateNewComponentTypeCommand;
import com.tocea.corolla.products.dao.IComponentTypeDAO;
import com.tocea.corolla.products.domain.ComponentType;
import com.tocea.corolla.products.exceptions.InvalidComponentTypeException;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class EditComponentTypeCommandHandler implements
ICommandHandler<CreateNewComponentTypeCommand, ComponentType> {

	@Autowired
	private IComponentTypeDAO	componentTypeDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang
	 * .Object)
	 */
	@Override
	public ComponentType handle(final CreateNewComponentTypeCommand _command) {
		final ComponentType componentType = _command.getComponentType();
		Validate.notNull(componentType, "Component type should exist");
		if (componentType.getId() != null) {
			throw new InvalidComponentTypeException();
		}
		this.componentTypeDAO.save(componentType);
		return componentType;
	}
}
