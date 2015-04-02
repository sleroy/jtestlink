/**
 *
 */
package com.tocea.corolla.products.domain;

import java.util.Date;

/**
 * This class defines a version for a component or a product.
 *
 * @author sleroy
 *
 */
public class ArtifactVersion {

	private Integer				id;

	private String				name;

	private String				description;


	private Date				releaseDate;

	private String				type;

	private Integer				productOwner;


	private Integer	componentOwner;

	/**
	 * @return the componentOwner
	 */
	public Integer getComponentOwner() {
		return this.componentOwner;
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
	 * @return the productOwner
	 */
	public Integer getProductOwner() {
		return this.productOwner;
	}

	/**
	 * @return the releaseDate
	 */
	public Date getReleaseDate() {
		return this.releaseDate;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param _componentOwner
	 *            the componentOwner to set
	 */
	public void setComponentOwner(final Integer _componentOwner) {
		this.componentOwner = _componentOwner;
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
	 * @param _productOwner
	 *            the productOwner to set
	 */
	public void setProductOwner(final Integer _productOwner) {
		this.productOwner = _productOwner;
	}

	/**
	 * @param _releaseDate
	 *            the releaseDate to set
	 */
	public void setReleaseDate(final Date _releaseDate) {
		this.releaseDate = _releaseDate;
	}

	/**
	 * @param _type
	 *            the type to set
	 */
	public void setType(final String _type) {
		this.type = _type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ArtifactVersion [id=" + this.id + ", name=" + this.name
				+ ", description=" + this.description + ", releaseDate="
				+ this.releaseDate + ", type=" + this.type + ", productOwner="
				+ this.productOwner + ", componentOwner=" + this.componentOwner
				+ "]";
	}

}
