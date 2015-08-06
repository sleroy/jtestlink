/**
 *
 */
package com.tocea.corolla.users.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tocea.corolla.users.domain.Role;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */
public interface IRoleDAO extends MongoRepository<Role, String> {

	Role findRoleByName(String name);

	/**
	 * Returns the default role
	 * @return the default  role.
	 */
	@Query("{ 'defaultRole': true }")
	Role getDefaultRole();

}
