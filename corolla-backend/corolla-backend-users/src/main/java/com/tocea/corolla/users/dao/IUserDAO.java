/**
 *
 */
package com.tocea.corolla.users.dao;

import java.util.List;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.users.domain.User;

/**
 * This interface defines the CRUD to manipulate members.
 * 
 * @author sleroy
 *
 *
 */
@JaversSpringDataAuditable
public interface IUserDAO extends MongoRepository<User, String> {

	//void deleteUserByLogin(String login);

	/**
	 * Finds a user per login.
	 * 
	 * @param login
	 */
	User findUserByLogin(String login);
	
	/**
	 * Finds the users who have a specific role
	 * @param roleId
	 * @return
	 */
	List<User> findByRoleId(String roleId);
	
}
