/**
 *
 */
package com.tocea.corolla.customfields.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity()
@Table(name = "custom_field_types")
public class CustomFieldType {

	@Id
	@GeneratedValue
	private Integer	id;

	@NotBlank
	@Column(nullable = false, length = 64)
	private String	name;

	@NotBlank
	@Column(nullable = false, length = 64)
	private String	dataType;

	@NotNull
	@Column(nullable = false)
	private boolean	required;

	@NotBlank
	@Column(nullable = false, length = 64)
	private String	scope;

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return this.dataType;
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
	 * @return the scope
	 */
	public String getScope() {
		return this.scope;
	}

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return this.required;
	}

	/**
	 * @param _dataType
	 *            the dataType to set
	 */
	public void setDataType(final String _dataType) {
		this.dataType = _dataType;
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
	 * @param _required
	 *            the required to set
	 */
	public void setRequired(final boolean _required) {
		this.required = _required;
	}

	/**
	 * @param _scope
	 *            the scope to set
	 */
	public void setScope(final String _scope) {
		this.scope = _scope;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomFieldType [id=" + this.id + ", name=" + this.name + ", dataType="
				+ this.dataType + ", required=" + this.required + ", scope=" + this.scope
				+ "]";
	}

}
