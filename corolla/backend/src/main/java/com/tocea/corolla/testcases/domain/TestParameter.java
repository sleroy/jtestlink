package com.tocea.corolla.testcases.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity()
@Table(name = "test_parameters")
public class TestParameter implements Serializable {

	@Id
	@GeneratedValue
	private Integer	id;

	@NotBlank
	@Column(nullable = false, length= 64)
	private String	name;

	@NotBlank
	@Column(nullable = false, length=64)
	private String	type;

	@Lob
	@Column(nullable = false)
	private String	description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private TestCaseRevision	owner;

	public TestParameter() {
		super();
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
	 * @return the owner
	 */
	public TestCaseRevision getOwner() {
		return this.owner;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
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
	 * @param _owner the owner to set
	 */
	public void setOwner(final TestCaseRevision _owner) {
		this.owner = _owner;
	}

	/**
	 * @param _type
	 *            the type to set
	 */
	public void setType(final String _type) {
		this.type = _type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestParameter [id=" + this.id + ", name=" + this.name + ", type=" + this.type
				+ ", description=" + this.description + ", owner=" + this.owner + "]";
	}

}