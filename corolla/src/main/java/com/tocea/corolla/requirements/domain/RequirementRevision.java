/**
 *
 */
package com.tocea.corolla.requirements.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.tocea.corolla.attachments.domain.Attachment;
import com.tocea.corolla.customfields.domain.CustomField;
import com.tocea.corolla.products.domain.ProductVersion;
import com.tocea.corolla.users.domain.User;

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
	private Integer					id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private Requirement				owner;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updater_id", nullable = false)
	private User					updater;

	@Column(nullable = false)
	private int						version;

	@Column(nullable = false)
	private int						revision;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String					reference;

	@Column(nullable = false)
	private Date					modificationTime;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String					type;

	@NotBlank
	@Column(nullable = false, length = 128)
	private String					status;

	@NotBlank
	@Column(nullable = false, length = 64)
	private String					criticity;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String					modificationReason;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String					tags;

	@ManyToMany
	@JoinTable(name = "requirements_revision_components", joinColumns = { @JoinColumn(name = "req_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "component_id", referencedColumnName = "id") })
	private List<ProductVersion>	componentVersions	= Lists.newArrayList();

	@Lob
	@Column(nullable = false)
	private String					description;

	@OneToMany(mappedBy = "requirement_owner", fetch = FetchType.LAZY)
	private List<Attachment>		attachments;

	@OneToMany(mappedBy = "requirementOwner")
	private List<CustomField>		customFields;

	/**
	 * @return the attachments
	 */
	public List<Attachment> getAttachments() {
		return this.attachments;
	}

	/**
	 * @return the componentVersions
	 */
	public List<ProductVersion> getComponentVersions() {
		return this.componentVersions;
	}

	/**
	 * @return the criticity
	 */
	public String getCriticity() {
		return this.criticity;
	}

	/**
	 * @return the customFields
	 */
	public List<CustomField> getCustomFields() {
		return this.customFields;
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
	public Requirement getOwner() {
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
	public User getUpdater() {
		return this.updater;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return this.version;
	}

	/**
	 * @param _attachments
	 *            the attachments to set
	 */
	public void setAttachments(final List<Attachment> _attachments) {
		this.attachments = _attachments;
	}

	/**
	 * @param _componentVersions
	 *            the componentVersions to set
	 */
	public void setComponentVersions(
			final List<ProductVersion> _componentVersions) {
		this.componentVersions = _componentVersions;
	}

	/**
	 * @param _criticity
	 *            the criticity to set
	 */
	public void setCriticity(final String _criticity) {
		this.criticity = _criticity;
	}

	/**
	 * @param _customFields
	 *            the customFields to set
	 */
	public void setCustomFields(final List<CustomField> _customFields) {
		this.customFields = _customFields;
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
	public void setOwner(final Requirement _owner) {
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
	public void setUpdater(final User _updater) {
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
