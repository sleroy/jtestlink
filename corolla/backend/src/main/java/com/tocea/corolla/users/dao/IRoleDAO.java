/**
 *
 */
package com.tocea.corolla.users.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.users.domain.Role;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */
@RepositoryRestResource(path = "/roles", collectionResourceRel = "roles")
public interface IRoleDAO extends CrudRepository<Role, Integer> {

}
