package com.tocea.corolla.products.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class declares a product on which will be attached requirements / test
 * etc
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "products")
public class Product implements Serializable {

	@Id
	@GeneratedValue
	private Integer						id;

	@Column(nullable = false)
	private String						name;

	@Lob
	@Column(nullable = false)
	private String						description;

	@OneToMany(mappedBy = "owner")
	private List<ProductArchitecture>	architectures;

	/**
	 * @return the architectures
	 */
	public List<ProductArchitecture> getArchitectures() {
		return this.architectures;
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
	 * @param _architectures
	 *            the architectures to set
	 */
	public void setArchitectures(final List<ProductArchitecture> _architectures) {
		this.architectures = _architectures;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [id=" + this.id + ", name=" + this.name
				+ ", description=" + this.description + "]";
	}

}