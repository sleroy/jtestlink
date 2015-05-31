/**
 *
 */
package com.tocea.corolla.products.commands;

/**
 * @author sleroy
 *
 */
public class DeleteApplicationCommand {
	private Integer	productID;

	/**
	 * @param _productID
	 */
	public DeleteApplicationCommand(final Integer _productID) {
		super();
		this.productID = _productID;
	}

	/**
	 * @return the productID
	 */
	public Integer getProductID() {
		return this.productID;
	}

	/**
	 * @param _productID
	 *            the productID to set
	 */
	public void setProductID(final Integer _productID) {
		this.productID = _productID;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DeleteApplicationCommand [productID=" + this.productID + "]";
	}

}
