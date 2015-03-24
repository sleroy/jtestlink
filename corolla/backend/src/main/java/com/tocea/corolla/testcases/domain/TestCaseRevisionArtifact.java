/**
 *
 */
package com.tocea.corolla.testcases.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "testcaserev_artifact")
public class TestCaseRevisionArtifact {

	@Column(nullable = false)
	@NotNull
	private Integer	testCaseRevisionId;

	@Column(nullable = false)
	@NotNull
	private Integer	artifactId;

	public TestCaseRevisionArtifact() {
		super();
	}

	/**
	 * @return the artifactId
	 */
	public Integer getArtifactId() {
		return this.artifactId;
	}

	/**
	 * @return the testCaseRevisionId
	 */
	public Integer getTestCaseRevisionId() {
		return this.testCaseRevisionId;
	}

	/**
	 * @param _artifactId
	 *            the artifactId to set
	 */
	public void setArtifactId(final Integer _artifactId) {
		this.artifactId = _artifactId;
	}

	/**
	 * @param _requirementId
	 *            the testCaseRevisionId to set
	 */
	public void setTestCaseRevisionId(final Integer _requirementId) {
		this.testCaseRevisionId = _requirementId;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCaseRevisionArtifact [testCaseRevisionId=" + this.testCaseRevisionId
				+ ", artifactId=" + this.artifactId + "]";
	}

}
