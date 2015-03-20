package com.tocea.corolla.testcases.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity()
@Table(name = "test_datasets")
public class TestDataSet implements Serializable {

	@Id
	@GeneratedValue
	private Integer				id;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String				name;

	@NotBlank
	@Column(nullable = false, length = 512)
	private String				url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id")
	private TestCaseRevision	owner;

	public TestDataSet() {
		super();
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
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
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
	 * @param _owner
	 *            the owner to set
	 */
	public void setOwner(final TestCaseRevision _owner) {
		this.owner = _owner;
	}

	/**
	 * @param _url
	 *            the url to set
	 */
	public void setUrl(final String _url) {
		this.url = _url;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestDataSet [id=" + this.id + ", name=" + this.name + ", url="
				+ this.url + ", owner=" + this.owner + "]";
	}

}