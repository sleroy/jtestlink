/**
 *
 */
package com.tocea.corolla.attachments.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author sleroy
 *
 */
@Entity()
@Table(name = "attachments")
public class Attachment {
	@Id
	@GeneratedValue
	private Integer	id;

	// Requirement revision
	@Column(nullable = true)
	private Integer	requirementOwner;

	//TEst case

	@Column(nullable = true)
	private Integer	testCaseRevisionOwner;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String				name;

	@NotNull
	@Column(nullable = false, length = 256)
	private String				description;

	@NotBlank
	@Column(nullable = false, length = 512)
	private String				url;

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
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
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
	 * @param _requirement_owner
	 *            the requirementOwner to set
	 */
	public void setRequirementOwner(
			final Integer _requirement_owner) {
		this.requirementOwner = _requirement_owner;
	}

	/**
	 * @param _testCase_owner the testCaseRevisionOwner to set
	 */
	public void setTestCaseRevisionOwner(final Integer _testCase_owner) {
		this.testCaseRevisionOwner = _testCase_owner;
	}

	/**
	 * @param _url
	 *            the url to set
	 */
	public void setUrl(final String _url) {
		this.url = _url;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Attachment [id=" + this.id + ", requirementOwner="
				+ this.requirementOwner + ", testCaseRevisionOwner=" + this.testCaseRevisionOwner
				+ ", name=" + this.name + ", description=" + this.description + ", url="
				+ this.url + "]";
	}
}
