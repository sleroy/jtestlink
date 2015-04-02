/**
 *
 */
package com.tocea.corolla.users.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * @author sleroy
 *
 */
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

	public static final Role	DEFAULT_ROLE	= new Role("MISSING_ROLE",
	                        	            	           "Missing role",
	                        	            	           "",
	                        	            	           false);

	@Id
	@GeneratedValue
	private Integer				id;

	@Size(min = 1, max = 30)
	@NotBlank
	@Column(nullable = false)
	private String				name;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String				note;

	@NotBlank
	@Column(nullable = false, length = 256)
	private String				permissions;

	@Column(nullable = false)
	@NotNull
	private boolean				defaultRole;

	/**
	 *
	 */
	public Role() {
		super();
	}

	/**
	 * @param _name
	 * @param _note
	 * @param _permissions
	 * @param _defaultRole
	 */
	public Role(final String _name, final String _note,
			final String _permissions, final boolean _defaultRole) {
		super();
		this.name = _name;
		this.note = _note;
		this.permissions = _permissions;
		this.defaultRole = _defaultRole;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {

		return this.permissions;
	}
	/**
	 * @return the spring security authorities.

	 */
	public List<GrantedAuthority> getGrantedAuthorities() {
		final Iterable<String> permissionIterator = Splitter.on(',')
				.trimResults()
				.omitEmptyStrings()
				.split(	this.permissions);
		final ArrayList<GrantedAuthority> authorities = Lists.newArrayList();
		for (final String permission : permissionIterator) {
			authorities.add(new SimpleGrantedAuthority(permission));
		}
		return authorities;
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
	 * @param _defaultRole
	 *            the defaultRole to set
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
	 * @param _permissions
	 *            the permissions to set
	 */
	public void setPermissions(final String _permissions) {
		this.permissions = _permissions;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Role [id=" + this.id + ", name=" + this.name + ", note="
				+ this.note + "]";
	}
}
