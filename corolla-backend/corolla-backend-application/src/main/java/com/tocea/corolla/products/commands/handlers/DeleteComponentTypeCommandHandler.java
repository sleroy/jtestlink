/**
 *
 */
package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.DeleteComponentTypeCommand;
import com.tocea.corolla.products.dao.IComponentTypeDAO;
import com.tocea.corolla.products.domain.ComponentType;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class DeleteComponentTypeCommandHandler implements
ICommandHandler<DeleteComponentTypeCommand, Boolean> {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(DeleteComponentTypeCommandHandler.class);

	@Autowired
	private IComponentTypeDAO	componentTypeDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang
	 * .Object)
	 */
	@Override
	public Boolean handle(final DeleteComponentTypeCommand _command) {
		final Integer componentTypeID = _command.getComponentTypeID();
		final ComponentType componentType = this.componentTypeDAO.findOne(componentTypeID);
		final Boolean found = componentType != null;
		if (!found) {
			LOGGER.warn("Component type not found {}", componentTypeID);
		} else {
			this.componentTypeDAO.delete(componentType);
		}
		return found;
	}

}
