/**
 *
 */
package com.tocea.corolla.users.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.users.domain.User;

/**
 * This interface defines the CRUD to manipulate members.
 * @author sleroy
 *
 *
 */
@RepositoryRestResource(path="/members", collectionResourceRel="members")
public interface IUserDAO extends CrudRepository<User, Integer> {

}
