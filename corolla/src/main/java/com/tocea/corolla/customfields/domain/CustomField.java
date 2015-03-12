/**
 *
 */
package com.tocea.corolla.customfields.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.products.domain.ProductComponent;
import com.tocea.corolla.requirements.domain.RequirementRevision;

@Entity()
@Table(name = "custom_fields")
public class CustomField {

	@Id
	@GeneratedValue
	private Integer	id;

	@NotBlank
	@Column(nullable = false, length = 64)
	private String	name;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String	value;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POWNER_ID")
	private Product				productOwner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COWNER_ID")
	private ProductComponent	componentOwner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROWNER_ID")
	private RequirementRevision	requirementOwner;

	/**
	 * @return the componentOwner
	 */
	public ProductComponent getComponentOwner() {
		return this.componentOwner;
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
	 * @return the requirementOwner
	 */
	public RequirementRevision getRequirementOwner() {
		return this.requirementOwner;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * @param _componentOwner
	 *            the componentOwner to set
	 */
	public void setComponentOwner(final ProductComponent _componentOwner) {
		this.componentOwner = _componentOwner;
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
	 * @param _requirementOwner
	 *            the requirementOwner to set
	 */
	public void setRequirementOwner(final RequirementRevision _requirementOwner) {
		this.requirementOwner = _requirementOwner;
	}

	/**
	 * @param _value
	 *            the value to set
	 */
	public void setValue(final String _value) {
		this.value = _value;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomField [id=" + this.id + ", name=" + this.name + ", value=" + this.value
				+ ", productOwner=" + this.productOwner + ", componentOwner="
				+ this.componentOwner + ", requirementOwner=" + this.requirementOwner
				+ "]";
	}

}
