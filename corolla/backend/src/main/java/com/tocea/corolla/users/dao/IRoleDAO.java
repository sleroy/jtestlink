/**
 *
 */
package com.tocea.corolla.users.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tocea.corolla.users.domain.Role;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */

public interface IRoleDAO extends CrudRepository<Role, Integer> {

	Role findRoleByName(String name);

	/**
	 * Returns the default role
	 * @return the default  role.
	 */
	@Query("SELECT r FROM Role r where r.defaultRole = true")
	Role getDefaultRole();

}
