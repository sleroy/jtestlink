/*
 * Corolla - A Tool to manage software requirements and test cases 
 * Copyright (C) 2015 Tocea
 * 
 * This file is part of Corolla.
 * 
 * Corolla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, 
 * or any later version.
 * 
 * Corolla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.customfields.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

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

	@Column(nullable = true)
	private Integer				productOwner;
	@Column(nullable = true)
	private Integer	componentOwner;

	// requirement version
	@Column(nullable = true)
	private Integer	requirementOwner;

	@Column(nullable = true)
	private Integer	testCaseRevisionOwner;

	/**
	 * @return the componentOwner
	 */
	public Integer getComponentOwner() {
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
	public Integer getProductOwner() {
		return this.productOwner;
	}

	/**
	 * @return the requirementOwner
	 */
	public Integer getRequirementOwner() {
		return this.requirementOwner;
	}

	/**
	 * @return the testCaseRevisionOwner
	 */
	public Integer getTestCaseRevisionOwner() {
		return this.testCaseRevisionOwner;
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
	public void setComponentOwner(final Integer _componentOwner) {
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
	public void setProductOwner(final Integer _productOwner) {
		this.productOwner = _productOwner;
	}

	/**
	 * @param _requirementOwner
	 *            the requirementOwner to set
	 */
	public void setRequirementOwner(final Integer _requirementOwner) {
		this.requirementOwner = _requirementOwner;
	}

	/**
	 * @param _testCaseRevisionOwner the testCaseRevisionOwner to set
	 */
	public void setTestCaseRevisionOwner(final Integer _testCaseRevisionOwner) {
		this.testCaseRevisionOwner = _testCaseRevisionOwner;
	}

	/**
	 * @param _value
	 *            the value to set
	 */
	public void setValue(final String _value) {
		this.value = _value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomField [id=" + this.id + ", name=" + this.name + ", value=" + this.value
				+ ", productOwner=" + this.productOwner + ", componentOwner="
				+ this.componentOwner + ", requirementOwner=" + this.requirementOwner
				+ ", testCaseRevisionOwner=" + this.testCaseRevisionOwner + "]";
	}

}
