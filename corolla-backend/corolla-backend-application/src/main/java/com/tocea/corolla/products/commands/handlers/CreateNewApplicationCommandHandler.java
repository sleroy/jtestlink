/**
 *
 */
package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.CreateNewApplicationCommand;
import com.tocea.corolla.products.dao.IApplicationDAO;
import com.tocea.corolla.products.domain.Application;
import com.tocea.corolla.products.exceptions.InvalidProductException;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class CreateNewApplicationCommandHandler implements
ICommandHandler<CreateNewApplicationCommand, Application> {

	private static final String	HTTP_DUMMYIMAGE_COM_50X50_TEXT	= "http://dummyimage.com/50x50&text=";
	@Autowired
	private IApplicationDAO	applicationDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang
	 * .Object)
	 */
	@Override
	public Application handle(final CreateNewApplicationCommand _command) {
		final Application application = _command.getProduct();
		Validate.notNull(application, "Application should exist");
		if (application.getId() != null) {
			throw new InvalidProductException();
		}
		if (application.getImage() == null) {
			application.setImage(HTTP_DUMMYIMAGE_COM_50X50_TEXT
					+ application.getKey());
		}
		this.applicationDAO.save(application);
		return application;
	}
}
