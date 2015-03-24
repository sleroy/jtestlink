package com.tocea.corolla.testcases.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity()
@Table(name = "testcases")
public class TestCase implements Serializable {

	@Id
	@GeneratedValue
	private Integer		id;

	@Column(nullable = false)
	@NotNull
	private Integer		author;

	@NotNull
	@Column(nullable = false)
	private Date		created_time;

	@Column(nullable = true)
	private Integer	parentTestCase;


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
	 * @return the parentTestCase
	 */
	public Integer getParentTestCase() {
		return this.parentTestCase;
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



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TestCase [id=" + this.id + ", author=" + this.author + ", created_time="
				+ this.created_time + ", parentTestCase=" + this.parentTestCase + "]";
	}

}