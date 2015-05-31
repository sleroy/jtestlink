package com.tocea.corolla.products.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class declares a product on which will be attached requirements / test
 * etc
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "products")
public class Application implements Serializable {

	@Id
	@GeneratedValue
	private Integer				id;

	@NotBlank
	@Column(nullable = false, length = 255)
	private String				name;

	@NotBlank
	@Column(nullable = false, length = 40)
	private String				key;

	@Lob
	@Column(nullable = false)
	private String				description;

	@Column(nullable = false, length = 255)
	private String				image;

	@Column(nullable = false)
	@NotNull
	private ApplicationStatus	status	= null;

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
	 * @return the image
	 */
	public String getImage() {
		return this.image;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the status
	 */
	public ApplicationStatus getStatus() {
		return this.status;
	}

	@JsonIgnore
	public boolean hasId() {
		return this.id != null;
	}

	public boolean isActive() {
		return this.status == ApplicationStatus.ACTIVE;
	}

	public boolean isArchived() {
		return this.status == ApplicationStatus.ARCHIVED;
	}

	public boolean isOnHod() {
		return this.status == ApplicationStatus.ONHOLD;
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
	 * @param _image
	 *            the image to set
	 */
	public void setImage(final String _image) {
		this.image = _image;
	}

	/**
	 * @param _key
	 *            the key to set
	 */
	public void setKey(final String _key) {
		this.key = _key;
	}

	/**
	 * @param _name
	 *            the name to set
	 */
	public void setName(final String _name) {
		this.name = _name;
	}

	/**
	 * @param _status
	 *            the status to set
	 */
	public void setStatus(final ApplicationStatus _status) {
		this.status = _status;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Application [id=" + this.id + ", name=" + this.name + ", key=" + this.key
				+ ", description=" + this.description + ", image=" + this.image
				+ ", status=" + this.status + "]";
	}

}