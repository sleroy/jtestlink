package com.tocea.corolla.requirements.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This class declares a requirement.
 *
 * @author sleroy
 *
 */
@Entity()
@Table(name = "requirements")
public class Requirement implements Serializable {

	@Id
	@GeneratedValue
	private Integer						id;

	@NotNull
	@Column(nullable = false)
	private Integer						author;

	@NotNull
	@Column(nullable = false)
	private Date						created_time;

	@Column(nullable = true)
	private Integer					parentRequirement;


	/**
	 * @return the author
	 */
	public Integer getAuthor() {
		return this.author;
	}

	/**
	 * @return the created_time
	 */
	public Date getCreated_time() {
		return this.created_time;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @return the parentRequirement
	 */
	public Integer getParent() {
		return this.parentRequirement;
	}

	/**
	 * @param _author
	 *            the author to set
	 */
	public void setAuthor(final Integer _author) {
		this.author = _author;
	}



	/**
	 * @param _created_time
	 *            the created_time to set
	 */
	public void setCreated_time(final Date _created_time) {
		this.created_time = _created_time;
	}

	/**
	 * @param _id
	 *            the id to set
	 */
	public void setId(final Integer _id) {
		this.id = _id;
	}

	/**
	 * @param _parent
	 *            the parentRequirement to set
	 */
	public void setParent(final Integer _parent) {
		this.parentRequirement = _parent;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Requirement [id=" + this.id + ", author=" + this.author
				+ ", created_time=" + this.created_time + ", parentRequirement="
				+ this.parentRequirement + "]";
	}

}