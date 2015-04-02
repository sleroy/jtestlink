package com.tocea.corolla.products.domain;

import java.io.Serializable;

/**
 * This class declares a component that is a part of a product.
 *
 * @author sleroy
 *
 */
public class ProductComponent implements Serializable {

	private Integer	id;

	private Integer	productOwner;

	private Integer	componentType;

	private String	name;

	private String	description;

	private Integer	parentComponent;

	/**
	 * @return the componentType
	 */
	public Integer getComponentType() {
		return this.componentType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the parentComponent
	 */
	public Integer getParentComponent() {
		return this.parentComponent;
	}

	/**
	 * @return the productOwner
	 */
	public Integer getProductOwner() {
		return this.productOwner;
	}

	/**
	 * @param _componentType
	 *            the componentType to set
	 */
	public void setComponentType(final Integer _componentType) {
		this.componentType = _componentType;
	}

	/**
	 * @param _description
	 *            the description to set
	 */
	public void setDescription(final String _description) {
		this.description = _description;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _name
	 *            the name to set
	 */
	public void setName(final String _name) {
		this.name = _name;
	}

	/**
	 * @param _parentComponent
	 *            the parentComponent to set
	 */
	public void setParentComponent(final Integer _parentComponent) {
		this.parentComponent = _parentComponent;
	}

	/**
	 * @param _productOwner
	 *            the productOwner to set
	 */
	public void setProductOwner(final Integer _productOwner) {
		this.productOwner = _productOwner;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProductComponent [id=" + this.id + ", productOwner=" + this.productOwner
				+ ", componentType=" + this.componentType + ", name=" + this.name
				+ ", description=" + this.description + ", parentComponent="
				+ this.parentComponent + "]";
	}

}