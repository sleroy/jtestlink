/**
 *
 */
package com.tocea.corolla.users.domain

import org.springframework.security.core.GrantedAuthority

/**
 * @author sleroy
 *
 */
public class Role implements GrantedAuthority {

	public static final Role	DEFAULT_ROLE	= new Role(name:"MISSING_ROLE",
	note:"Missing role",
	permissions:"",
	defaultRole:false)

	private Integer				id

	private String				name

	private String				note

	private String				permissions

	private boolean				defaultRole

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {

		return permissions
	}
}