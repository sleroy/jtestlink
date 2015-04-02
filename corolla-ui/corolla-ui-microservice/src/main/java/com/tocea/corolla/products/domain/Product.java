package com.tocea.corolla.products.domain;

import java.io.Serializable;

/**
 * This class declares a product on which will be attached requirements / test
 * etc
 *
 * @author sleroy
 *
 */
public class Product implements Serializable {

	private Integer					id;

	private String					name;

	private String					description;


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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [id=" + this.id + ", name=" + this.name + ", description="
				+ this.description + "]";
	}

}