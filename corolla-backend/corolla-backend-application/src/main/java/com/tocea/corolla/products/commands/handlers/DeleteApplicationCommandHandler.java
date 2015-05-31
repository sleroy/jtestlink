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
import com.tocea.corolla.products.commands.DeleteApplicationCommand;
import com.tocea.corolla.products.dao.IApplicationDAO;
import com.tocea.corolla.products.domain.Application;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class DeleteApplicationCommandHandler implements
ICommandHandler<DeleteApplicationCommand, Boolean> {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(DeleteApplicationCommandHandler.class);

	@Autowired
	private IApplicationDAO			applicationDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang
	 * .Object)
	 */
	@Override
	public Boolean handle(final DeleteApplicationCommand _command) {
		final Integer productID = _command.getProductID();
		final Application application = this.applicationDAO.findOne(productID);
		final Boolean found = application != null;
		if (!found) {
			LOGGER.warn("Application not found {}", productID);
		} else {
			this.applicationDAO.delete(application);
		}
		return found;
	}

}
