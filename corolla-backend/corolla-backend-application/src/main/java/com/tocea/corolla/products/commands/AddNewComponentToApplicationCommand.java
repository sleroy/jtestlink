/**
 *
 */
package com.tocea.corolla.products.commands;

import com.tocea.corolla.cqrs.annotations.Command;

/**
 * This class defines a new action to perform.
 *
 * @author sleroy
 *
 */
@Command
public class AddNewComponentToApplicationCommand {
	private Integer	productID;
	private Integer	componentTypeID;
	private Integer	parentComponentID;
	private String	name;
	private String	description;

	public AddNewComponentToApplicationCommand() {
		super();
	}

	/**
	 * This class defines the new commands.
	 *
	 * @param _parentComponentId
	 * @param componentTYpeID
	 */
	public AddNewComponentToApplicationCommand(final Integer _parentComponentId,
			final Integer componentTYpeID) {
		this.productID = _parentComponentId;
		this.componentTypeID = componentTYpeID;
	}

	/**
	 * @return the componentTypeID
	 */
	public Integer getComponentTypeID() {
		return this.componentTypeID;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the parentComponentID
	 */
	public Integer getParentComponentID() {
		return this.parentComponentID;
	}

	/**
	 * @return the productID
	 */
	public Integer getProductID() {
		return this.productID;
	}

	/**
	 * @param _architectureTypeID
	 *            the componentTypeID to set
	 */
	public void setComponentTypeID(final Integer _architectureTypeID) {
		this.componentTypeID = _architectureTypeID;
	}

	/**
	 * @param _description
	 *            the description to set
	 */
	public void setDescription(final String _description) {
		this.description = _description;
	}

	/**
	 * @param _name
	 *            the name to set
	 */
	public void setName(final String _name) {
		this.name = _name;
	}

	/**
	 * @param _parentArchitectureID
	 *            the parentComponentID to set
	 */
	public void setParentComponentID(final Integer _parentArchitectureID) {
		this.parentComponentID = _parentArchitectureID;
	}

	/**
	 * @param _productID
	 *            the productID to set
	 */
	public void setProductID(final Integer _productID) {
		this.productID = _productID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddNewComponentToApplicationCommand [productID=" + this.productID
				+ ", componentTypeID=" + this.componentTypeID
				+ ", parentComponentID=" + this.parentComponentID + ", name="
				+ this.name + ", description=" + this.description + "]";
	}

}
