package com.tocea.corolla.testcases.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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

	@Column(nullable = false)
	@NotNull
	private Integer	testCaseRevision;

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
	 * @return the testCaseRevision
	 */
	public Integer getTestCaseRevision() {
		return this.testCaseRevision;
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
	 *            the testCaseRevision to set
	 */
	public void setTestCaseRevision(final Integer _owner) {
		this.testCaseRevision = _owner;
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
				+ this.url + ", testCaseRevision=" + this.testCaseRevision + "]";
	}

}