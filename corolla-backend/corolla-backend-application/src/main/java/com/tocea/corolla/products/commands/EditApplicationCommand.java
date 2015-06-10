/**
 *
 */
package com.tocea.corolla.products.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.products.domain.Application;

/**
 * @author sleroy
 *
 */
@Command
public class EditApplicationCommand {
	
	@NotNull
	private Application	application;
	
	public EditApplicationCommand() {
		super();
	}
	
	public EditApplicationCommand(Application _application) {
		super();
		this.application = _application;
	}

	/**
	 * @return the application
	 */
	public Application getProduct() {
		return this.application;
	}

	/**
	 * @param _application
	 *            the application to set
	 */
	public void setProduct(final Application _application) {
		this.application = _application;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreateNewApplicationCommand [application=" + this.application + "]";
	}
}
