package com.tocea.corolla.products.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.customfields.domain.CustomField;

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
	private Integer					id;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String					name;

	@Lob
	@Column(nullable = false)
	private String					description;

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<ProductComponent>	architectures;

	@OneToMany(mappedBy = "productOwner", fetch = FetchType.LAZY)
	private List<ProductVersion>	versions;

	@OneToMany(mappedBy = "productOwner", fetch = FetchType.LAZY)
	private List<CustomField>		customFields;

	/**
	 * @return the architectures
	 */
	public List<ProductComponent> getArchitectures() {
		return this.architectures;
	}

	/**
	 * @return the customFields
	 */
	public List<CustomField> getCustomFields() {
		return this.customFields;
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
	 * @return the versions
	 */
	public List<ProductVersion> getVersions() {
		return this.versions;
	}

	/**
	 * @param _architectures
	 *            the architectures to set
	 */
	public void setArchitectures(final List<ProductComponent> _architectures) {
		this.architectures = _architectures;
	}

	/**
	 * @param _customFields
	 *            the customFields to set
	 */
	public void setCustomFields(final List<CustomField> _customFields) {
		this.customFields = _customFields;
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
	 * @param _versions
	 *            the versions to set
	 */
	public void setVersions(final List<ProductVersion> _versions) {
		this.versions = _versions;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [id=" + this.id + ", name=" + this.name
				+ ", description=" + this.description + ", architectures="
				+ this.architectures + ", versions=" + this.versions
				+ ", customFields=" + this.customFields + "]";
	}

}