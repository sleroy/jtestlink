/**
 *
 */
package com.tocea.corolla.requirements.domain;

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
@Table(name = "requirement_artifact_version")
public class RequirementArtifactVersion {

	@Id
	@GeneratedValue
	private Integer		id;

	@Column(nullable = false)
	@NotNull
	private Integer	requirementId;

	@Column(nullable = false)
	@NotNull
	private Integer	artifactId;

	public RequirementArtifactVersion() {
		super();
	}

	/**
	 * @return the artifactId
	 */
	public Integer getArtifactId() {
		return this.artifactId;
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
	 * @param _artifactId
	 *            the artifactId to set
	 */
	public void setArtifactId(final Integer _artifactId) {
		this.artifactId = _artifactId;
	}

	/**
	 * @param _id the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _requirementId
	 *            the requirementId to set
	 */
	public void setRequirementId(final Integer _requirementId) {
		this.requirementId = _requirementId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequirementArtifactVersion [id=" + this.id + ", requirementId="
				+ this.requirementId + ", artifactId=" + this.artifactId + "]";
	}

}
