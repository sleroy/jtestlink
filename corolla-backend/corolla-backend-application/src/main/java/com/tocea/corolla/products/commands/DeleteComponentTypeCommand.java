/**
 *
 */
package com.tocea.corolla.products.commands;

/**
 * @author sleroy
 *
 */
public class DeleteComponentTypeCommand {
	private Integer	componentTypeID;

	/**
	 * @param _productID
	 */
	public DeleteComponentTypeCommand(final Integer _productID) {
		super();
		this.componentTypeID = _productID;
	}

	/**
	 * @return the componentTypeID
	 */
	public Integer getComponentTypeID() {
		return this.componentTypeID;
	}

	/**
	 * @param _productID
	 *            the componentTypeID to set
	 */
	public void setComponentTypeID(final Integer _productID) {
		this.componentTypeID = _productID;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeleteApplicationCommand [componentTypeID=" + this.componentTypeID + "]";
	}

}
