/**
 *
 */
package com.tocea.corolla.products.commands;

import com.tocea.corolla.products.domain.ComponentType;

/**
 * @author sleroy
 *
 */
public class EditComponentTypeCommand {
	private ComponentType	componentType;

	public EditComponentTypeCommand() {
		super();
	}

	public EditComponentTypeCommand(
			final ComponentType _componentType) {
		super();
		this.componentType = _componentType;
	}

	/**
	 * @return the componentType
	 */
	public ComponentType getComponentType() {
		return this.componentType;
	}

	/**
	 * @param _componentType
	 *            the componentType to set
	 */
	public void setComponentType(final ComponentType _componentType) {
		this.componentType = _componentType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreateNewComponentTypeCommand [componentType="
				+ this.componentType + "]";
	}

}
