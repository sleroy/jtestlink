/**
 *
 */
package com.tocea.corolla.testcases.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "testcaserev_requirement")
public class TestCaseRevisionRequirement {

	@Id
	@GeneratedValue
	private Integer		id;

	@Column(nullable = false)
	@NotNull
	private Integer	testCaseRevisionId;

	@Column(nullable = false)
	@NotNull
	private Integer	requirementId;

	public TestCaseRevisionRequirement() {
		super();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the requirementId
	 */
	public Integer getRequirementId() {
		return this.requirementId;
	}

	/**
	 * @return the testCaseRevisionId
	 */
	public Integer getTestCaseRevisionId() {
		return this.testCaseRevisionId;
	}

	/**
	 * @param _id the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _artifactId
	 *            the requirementId to set
	 */
	public void setRequirementId(final Integer _artifactId) {
		this.requirementId = _artifactId;
	}

	/**
	 * @param _requirementId
	 *            the testCaseRevisionId to set
	 */
	public void setTestCaseRevisionId(final Integer _requirementId) {
		this.testCaseRevisionId = _requirementId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCaseRevisionRequirement [id=" + this.id
				+ ", testCaseRevisionId=" + this.testCaseRevisionId
				+ ", requirementId=" + this.requirementId + "]";
	}

}
