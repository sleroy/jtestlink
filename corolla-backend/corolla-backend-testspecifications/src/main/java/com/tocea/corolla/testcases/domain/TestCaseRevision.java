/**
 *
 */
package com.tocea.corolla.testcases.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.users.domain.User;

@Entity()
@Table(name = "testcase_revisions")
public class TestCaseRevision {
	@Id
	@GeneratedValue
	private Integer		id;

	@NotNull
	@Column(nullable = false)
	private TestCase	ownerTestCase;

	@NotNull
	@Column(nullable = false)
	private Date		modificationTime;

	@NotNull
	@Column(nullable = false)
	private Integer			version;

	@NotNull
	@Column(nullable = false)
	private Integer			revision;

	@NotNull
	@NotBlank
	@Column(nullable = false, length = 128)
	private String					reference;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String					type;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String					status;

	@NotBlank
	@Column(nullable = false, length = 32)
	private String					criticity;

	@NotBlank
	@Column(nullable = false, length = 32)
	private String					importance;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String					modificationReason;

	@NotNull
	@Column(nullable = false, length = 256)
	private String					tags;



	@NotNull
	@Column(nullable = false)
	private User					updater;

	@Column(nullable = false)
	private Double					estimatedTime	= 0d;

	@Lob
	@Column(nullable = false)
	private String					summary;

	@Lob
	@Column(nullable = false)
	private String					preconditions;




	/**
	 * @return the criticity
	 */
	public String getCriticity() {
		return this.criticity;
	}


	/**
	 * @return the estimatedTime
	 */
	public Double getEstimatedTime() {
		return this.estimatedTime;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the importance
	 */
	public String getImportance() {
		return this.importance;
	}

	/**
	 * @return the modificationReason
	 */
	public String getModificationReason() {
		return this.modificationReason;
	}

	/**
	 * @return the modificationTime
	 */
	public Date getModificationTime() {
		return this.modificationTime;
	}

	/**
	 * @return the ownerTestCase
	 */
	public TestCase getOwnerTestCase() {
		return this.ownerTestCase;
	}


	/**
	 * @return the preconditions
	 */
	public String getPreconditions() {
		return this.preconditions;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return this.reference;
	}



	/**
	 * @return the revision
	 */
	public Integer getRevision() {
		return this.revision;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}


	/**
	 * @return the summary
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return this.tags;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @return the updater
	 */
	public User getUpdater() {
		return this.updater;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return this.version;
	}



	/**
	 * @param _criticity
	 *            the criticity to set
	 */
	public void setCriticity(final String _criticity) {
		this.criticity = _criticity;
	}

	/**
	 * @param _estimatedTime
	 *            the estimatedTime to set
	 */
	public void setEstimatedTime(final Double _estimatedTime) {
		this.estimatedTime = _estimatedTime;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _importance
	 *            the importance to set
	 */
	public void setImportance(final String _importance) {
		this.importance = _importance;
	}

	/**
	 * @param _modificationReason
	 *            the modificationReason to set
	 */
	public void setModificationReason(final String _modificationReason) {
		this.modificationReason = _modificationReason;
	}

	/**
	 * @param _modificationTime
	 *            the modificationTime to set
	 */
	public void setModificationTime(final Date _modificationTime) {
		this.modificationTime = _modificationTime;
	}

	/**
	 * @param _owner
	 *            the ownerTestCase to set
	 */
	public void setOwnerTestCase(final TestCase _owner) {
		this.ownerTestCase = _owner;
	}
	/**
	 * @param _preconditions
	 *            the preconditions to set
	 */
	public void setPreconditions(final String _preconditions) {
		this.preconditions = _preconditions;
	}

	/**
	 * @param _reference
	 *            the reference to set
	 */
	public void setReference(final String _reference) {
		this.reference = _reference;
	}



	/**
	 * @param _revision
	 *            the revision to set
	 */
	public void setRevision(final Integer _revision) {
		this.revision = _revision;
	}

	/**
	 * @param _status
	 *            the status to set
	 */
	public void setStatus(final String _status) {
		this.status = _status;
	}

	/**
	 * @param _description
	 *            the summary to set
	 */
	public void setSummary(final String _description) {
		this.summary = _description;
	}

	/**
	 * @param _tags
	 *            the tags to set
	 */
	public void setTags(final String _tags) {
		this.tags = _tags;
	}

	/**
	 * @param _type
	 *            the type to set
	 */
	public void setType(final String _type) {
		this.type = _type;
	}

	/**
	 * @param _updater
	 *            the updater to set
	 */
	public void setUpdater(final User _updater) {
		this.updater = _updater;
	}

	/**
	 * @param _version
	 *            the version to set
	 */
	public void setVersion(final Integer _version) {
		this.version = _version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCaseRevision [id=" + this.id + ", ownerTestCase="
				+ this.ownerTestCase + ", modificationTime=" + this.modificationTime
				+ ", version=" + this.version + ", revision=" + this.revision
				+ ", reference=" + this.reference + ", type=" + this.type + ", status="
				+ this.status + ", criticity=" + this.criticity + ", importance="
				+ this.importance + ", modificationReason=" + this.modificationReason
				+ ", tags=" + this.tags + ", updater=" + this.updater
				+ ", estimatedTime=" + this.estimatedTime + ", summary=" + this.summary
				+ ", preconditions=" + this.preconditions + "]";
	}
}
