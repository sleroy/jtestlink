/**
 *
 */
package com.tocea.corolla.products.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * This class defines a version for a component or a product.
 *
 * @author sleroy
 *
 */
public class ProductVersion {

	@Id
	@GeneratedValue
	private Integer				id;

	@Column(nullable = false, length = 64)
	private String				name;

	@Column(nullable = false, length = 256)
	private String				description;

	@Column(nullable = true)
	private Date				releaseDate;

	@Column(nullable = false, length = 64)
	private String				type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "powner_id")
	private Product				productOwner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cowner_id")
	private ProductComponent	componentOwner;

	/**
	 * @return the componentOwner
	 */
	public ProductComponent getComponentOwner() {
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
	public Product getProductOwner() {
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
	public void setComponentOwner(final ProductComponent _componentOwner) {
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
	public void setProductOwner(final Product _productOwner) {
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
		return "ProductVersion [id=" + this.id + ", name=" + this.name
				+ ", description=" + this.description + ", releaseDate="
				+ this.releaseDate + ", type=" + this.type + ", productOwner="
				+ this.productOwner + ", componentOwner=" + this.componentOwner
				+ "]";
	}

}
