/**
 *
 */
package com.tocea.corolla.attachments.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.requirements.domain.RequirementRevision;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private RequirementRevision	requirement_owner;

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
	 * @return the requirement_owner
	 */
	public RequirementRevision getRequirement_owner() {
		return this.requirement_owner;
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
	 *            the requirement_owner to set
	 */
	public void setRequirement_owner(
			final RequirementRevision _requirement_owner) {
		this.requirement_owner = _requirement_owner;
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
		return "Attachment [id=" + this.id + ", requirement_owner="
				+ this.requirement_owner + ", name=" + this.name + ", description="
				+ this.description + ", url=" + this.url + "]";
	}
}
