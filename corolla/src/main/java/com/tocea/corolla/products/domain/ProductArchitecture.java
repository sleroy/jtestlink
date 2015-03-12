package com.tocea.corolla.products.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class declares a product on which will be attached requirements / test
 * etc
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "product_architecture")
public class ProductArchitecture implements Serializable {

	@Id
	@GeneratedValue
	private Integer					id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private Product					owner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ARCHITECTURE_TYPE_ID")
	private ProductArchitectureType	type;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the owner
	 */
	public Product getOwner() {
		return this.owner;
	}

	/**
	 * @return the type
	 */
	public ProductArchitectureType getType() {
		return this.type;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _owner
	 *            the owner to set
	 */
	public void setOwner(final Product _owner) {
		this.owner = _owner;
	}

	/**
	 * @param _type
	 *            the type to set
	 */
	public void setType(final ProductArchitectureType _type) {
		this.type = _type;
	}

}