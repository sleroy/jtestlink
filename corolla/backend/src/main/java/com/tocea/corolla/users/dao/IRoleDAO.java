/**
 *
 */
package com.tocea.corolla.users.dao;

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

}
