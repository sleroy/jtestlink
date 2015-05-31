/**
 *
 */
package com.tocea.corolla.products.commands;

import com.tocea.corolla.products.domain.Application;

/**
 * @author sleroy
 *
 */
public class CreateNewApplicationCommand {
	private Application	application;

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
