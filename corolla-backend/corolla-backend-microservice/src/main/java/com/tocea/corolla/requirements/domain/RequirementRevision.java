/**
 *
 */
package com.tocea.corolla.requirements.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author sleroy
 *
 */
@Entity()
@Table(name = "requirement_revisions")
public class RequirementRevision {
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer	id;

	@NotNull
	@Column(nullable = false)
	private Integer	owner;

	@NotNull
	@Column(nullable = false)
	private Integer	updater;

	@NotNull
	@Column(nullable = false)
	private Integer	version;

	@NotNull
	@Column(nullable = false)
	private Integer	revision;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String	reference;

	@NotNull
	@Column(nullable = false)
	private Date	modificationTime;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String	type;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String	status;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String	modificationReason;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String	tags;

	@NotNull
	@Lob
	@Column(nullable = false)
	private String	description;

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
	 * @return the owner
	 */
	public Integer getOwner() {
		return this.owner;
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
	public int getRevision() {
		return this.revision;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
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
	public Integer getUpdater() {
		return this.updater;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return this.version;
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
	 *            the owner to set
	 */
	public void setOwner(final Integer _owner) {
		this.owner = _owner;
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
	public void setRevision(final int _revision) {
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
	public void setUpdater(final Integer _updater) {
		this.updater = _updater;
	}

	/**
	 * @param _version
	 *            the version to set
	 */
	public void setVersion(final int _version) {
		this.version = _version;
	}
}
