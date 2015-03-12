package com.tocea.corolla.requirements.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.tocea.corolla.users.domain.User;

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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id", nullable=false)
	private User						author;

	@NotNull
	@Column(nullable = false)
	private Date						created_time;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Requirement					parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Requirement>			children	= new HashSet<>();

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
	private List<RequirementRevision>	revisions;

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return this.author;
	}

	/**
	 * @return the children
	 */
	public Set<Requirement> getChildren() {
		return this.children;
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
	 * @return the parent
	 */
	public Requirement getParent() {
		return this.parent;
	}

	/**
	 * @return the revisions
	 */
	public List<RequirementRevision> getRevisions() {
		return this.revisions;
	}

	/**
	 * @param _author
	 *            the author to set
	 */
	public void setAuthor(final User _author) {
		this.author = _author;
	}

	/**
	 * @param _children
	 *            the children to set
	 */
	public void setChildren(final Set<Requirement> _children) {
		this.children = _children;
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
	 *            the parent to set
	 */
	public void setParent(final Requirement _parent) {
		this.parent = _parent;
	}

	/**
	 * @param _revisions
	 *            the revisions to set
	 */
	public void setRevisions(final List<RequirementRevision> _revisions) {
		this.revisions = _revisions;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Requirement [id=" + this.id + ", author=" + this.author
				+ ", created_time=" + this.created_time + ", parent=" + this.parent
				+ ", children=" + this.children + ", revisions=" + this.revisions + "]";
	}

}