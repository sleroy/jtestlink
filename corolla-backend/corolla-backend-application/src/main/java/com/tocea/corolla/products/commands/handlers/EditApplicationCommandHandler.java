/**
 *
 */
package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.EditApplicationCommand;
import com.tocea.corolla.products.dao.IApplicationDAO;
import com.tocea.corolla.products.domain.Application;
import com.tocea.corolla.products.exceptions.ProductNotFoundException;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class EditApplicationCommandHandler implements
ICommandHandler<EditApplicationCommand, Application> {

	@Autowired
	private IApplicationDAO	applicationDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang
	 * .Object)
	 */
	@Override
	public Application handle(final EditApplicationCommand _command) {
		final Application application = _command.getProduct();
		Validate.notNull(application, "Application should exist");
		if (application.getId() == null) {
			throw new ProductNotFoundException();
		}
		this.applicationDAO.save(application);
		return application;
	}
}
