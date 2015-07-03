/**
 *
 */
package com.tocea.corolla.products.commands.handlers;

import javax.transaction.Transactional;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.commands.AddNewComponentToApplicationCommand;
import com.tocea.corolla.products.dao.IComponentDAO;
import com.tocea.corolla.products.dao.IComponentTypeDAO;
import com.tocea.corolla.products.dao.IApplicationDAO;
import com.tocea.corolla.products.domain.Application;
import com.tocea.corolla.products.domain.Component;
import com.tocea.corolla.products.domain.ComponentType;

/**
 * @author sleroy
 *
 */
@CommandHandler
@Transactional
public class AddNewComponentToApplicationCommandHandler implements
ICommandHandler<AddNewComponentToApplicationCommand, Component> {

	@Autowired
	private IApplicationDAO		applicationDAO;

	@Autowired
	private IComponentTypeDAO	componentTypeDAO;
	@Autowired
	private IComponentDAO		componentDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.tocea.corolla.cqrs.handler.ICommandHandler#handle(java.lang
	 * .Object)
	 */
	@Override
	public Component handle(final AddNewComponentToApplicationCommand _command) {
		final Application application = this.applicationDAO.findOne(_command.getProductID());
		Validate.notNull(application, "Application should exist");
		final ComponentType componentType = this.componentTypeDAO.findOne(_command.getComponentTypeID());
		Validate.notNull(componentType, "Architecture type should exist");

		// Get the parent architecture if available
		Component parentProductArchitecture = null;
		if (_command.getParentComponentID() != null) {
			parentProductArchitecture = this.componentDAO.findOne(_command.getParentComponentID());
			Validate.notNull(	parentProductArchitecture,
					"Parent architecture should exist");
		}

		final Component component = this.createNewComponent(	_command, application,
		                                                    	componentType,
		                                                    	parentProductArchitecture);

		if (parentProductArchitecture == null) { // Attach to parent
			this.applicationDAO.save(application);
		} else {
			this.componentDAO.save(parentProductArchitecture);
		}

		return component;
	}

	/**
	 * Creates the new component
	 *
	 * @param _command
	 * @param application
	 * @param componentType
	 * @param parentProductArchitecture
	 * @return
	 */
	private Component createNewComponent(
			final AddNewComponentToApplicationCommand _command,
			final Application application, final ComponentType componentType,
			final Component parentProductArchitecture) {
		final Component component = new Component();
		component.setName(_command.getName());
		component.setDescription(_command.getDescription());
		if (parentProductArchitecture != null) {
			component.setParentComponent(parentProductArchitecture.getId());
		}
		component.setProductOwner(application.getId());
		component.setComponentType(componentType.getId());
		this.componentDAO.save(component);
		return component;
	}
}
