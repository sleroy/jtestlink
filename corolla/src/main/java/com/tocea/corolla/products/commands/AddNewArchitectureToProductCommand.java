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
public class AddNewArchitectureToProductCommand {
	private Integer	productID;
	private Integer	architectureTypeID;
	private Integer	parentArchitectureID;
	private String	name;
	private String	description;

	public AddNewArchitectureToProductCommand() {
		super();
	}

	/**
	 * This class defines the new commands.
	 *
	 * @param _productID2
	 * @param _architectureTypeID2
	 */
	public AddNewArchitectureToProductCommand(final Integer _productID2,
			final Integer _architectureTypeID2) {
		this.productID = _productID2;
		this.architectureTypeID = _architectureTypeID2;
	}

	/**
	 * @return the architectureTypeID
	 */
	public Integer getArchitectureTypeID() {
		return this.architectureTypeID;
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
	 * @return the parentArchitectureID
	 */
	public Integer getParentArchitectureID() {
		return this.parentArchitectureID;
	}

	/**
	 * @return the productID
	 */
	public Integer getProductID() {
		return this.productID;
	}

	/**
	 * @param _architectureTypeID
	 *            the architectureTypeID to set
	 */
	public void setArchitectureTypeID(final Integer _architectureTypeID) {
		this.architectureTypeID = _architectureTypeID;
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
	 *            the parentArchitectureID to set
	 */
	public void setParentArchitectureID(final Integer _parentArchitectureID) {
		this.parentArchitectureID = _parentArchitectureID;
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
		return "AddNewArchitectureToProductCommand [productID=" + this.productID
				+ ", architectureTypeID=" + this.architectureTypeID
				+ ", parentArchitectureID=" + this.parentArchitectureID + ", name="
				+ this.name + ", description=" + this.description + "]";
	}

}
