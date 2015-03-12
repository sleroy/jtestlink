/**
 *
 */
package com.tocea.corolla.testcases.domain;

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

import com.tocea.corolla.attachments.domain.Attachment;
import com.tocea.corolla.customfields.domain.CustomField;
import com.tocea.corolla.products.domain.ProductVersion;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.users.domain.User;

@Entity()
@Table(name = "testcase_revisions")
public class TestCaseRevision {
	@Id
	@GeneratedValue
	private Integer		id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private TestCase	owner;

	@NotNull
	@Column(nullable = false)
	private Date		modificationTime;

	@NotNull
	@Column(nullable = false)
	private int			version;

	@NotNull
	@Column(nullable = false)
	private int			revision;

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

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<TestCaseStep>		steps;

	@OneToMany(mappedBy = "parameters", fetch = FetchType.LAZY)
	private List<TestParameter>		parameters;

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<TestDataSet>		dataset;

	@ManyToMany
	@JoinTable(name = "TESTCASE_COMPONENTS", joinColumns = { @JoinColumn(name = "TCREV_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "COMPONENT_ID", referencedColumnName = "ID") })
	private List<ProductVersion>	componentVersions;

	@ManyToMany
	@JoinTable(name = "TESTCASE_REQUIREMENTS", joinColumns = { @JoinColumn(name = "TCREV_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "REQ_ID", referencedColumnName = "ID") })
	private List<Requirement>		requirements;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATER_ID")
	private User					updater;

	@Column(nullable = false)
	private Double					estimatedTime	= 0d;

	@Lob
	@Column(nullable = false)
	private String					summary;

	@Lob
	@Column(nullable = false)
	private String					preconditions;

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
	 * @return the dataset
	 */
	public List<TestDataSet> getDataset() {
		return this.dataset;
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
	 * @return the owner
	 */
	public TestCase getOwner() {
		return this.owner;
	}

	/**
	 * @return the parameters
	 */
	public List<TestParameter> getParameters() {
		return this.parameters;
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
	 * @return the requirements
	 */
	public List<Requirement> getRequirements() {
		return this.requirements;
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
	 * @return the steps
	 */
	public List<TestCaseStep> getSteps() {
		return this.steps;
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
	public void setComponentVersions(final List<ProductVersion> _componentVersions) {
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
	 * @param _dataset
	 *            the dataset to set
	 */
	public void setDataset(final List<TestDataSet> _dataset) {
		this.dataset = _dataset;
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
	 *            the owner to set
	 */
	public void setOwner(final TestCase _owner) {
		this.owner = _owner;
	}

	/**
	 * @param _parameters
	 *            the parameters to set
	 */
	public void setParameters(final List<TestParameter> _parameters) {
		this.parameters = _parameters;
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
	 * @param _requirements
	 *            the requirements to set
	 */
	public void setRequirements(final List<Requirement> _requirements) {
		this.requirements = _requirements;
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
	 * @param _steps
	 *            the steps to set
	 */
	public void setSteps(final List<TestCaseStep> _steps) {
		this.steps = _steps;
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
	public void setVersion(final int _version) {
		this.version = _version;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCaseRevision [id=" + this.id + ", owner=" + this.owner
				+ ", modificationTime=" + this.modificationTime + ", version="
				+ this.version + ", revision=" + this.revision + ", reference="
				+ this.reference + ", type=" + this.type + ", status=" + this.status
				+ ", criticity=" + this.criticity + ", importance=" + this.importance
				+ ", modificationReason=" + this.modificationReason + ", tags="
				+ this.tags + ", steps=" + this.steps + ", parameters=" + this.parameters
				+ ", dataset=" + this.dataset + ", componentVersions="
				+ this.componentVersions + ", requirements=" + this.requirements
				+ ", updater=" + this.updater + ", estimatedTime=" + this.estimatedTime
				+ ", summary=" + this.summary + ", preconditions=" + this.preconditions
				+ ", attachments=" + this.attachments + ", customFields="
				+ this.customFields + "]";
	}
}
