/**
 *
 */
package com.tocea.corolla.users.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.sun.istack.internal.NotNull;

/**
 * @author sleroy
 *
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable {
	@Id
	@GeneratedValue
	private Integer	id;

	@Size(min = 1, max = 30)
	@NotBlank
	@Column(nullable = false)
	private String	name;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String	note;

	@NotBlank
	@Column(nullable = false, length=256)
	private String	permissions;

	@Column(nullable = false)
	@NotNull
	private boolean defaultRole;


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
	 * @return the note
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * @return the permissions
	 */
	public String getPermissions() {
		return this.permissions;
	}

	/**
	 * @return the defaultRole
	 */
	public boolean isDefaultRole() {
		return this.defaultRole;
	}

	/**
	 * @param _defaultRole the defaultRole to set
	 */
	public void setDefaultRole(final boolean _defaultRole) {
		this.defaultRole = _defaultRole;
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
	 * @param _note
	 *            the note to set
	 */
	public void setNote(final String _note) {
		this.note = _note;
	}

	/**
	 * @param _permissions the permissions to set
	 */
	public void setPermissions(final String _permissions) {
		this.permissions = _permissions;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Role [id=" + this.id + ", name=" + this.name + ", note=" + this.note + "]";
	}

}
