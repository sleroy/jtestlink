/**
 *
 */
package com.tocea.corolla.users.dao;

import org.springframework.data.repository.CrudRepository;

import com.tocea.corolla.users.domain.User;

/**
 * This interface defines the CRUD to manipulate members.
 * 
 * @author sleroy
 *
 *
 */
public interface IUserDAO extends CrudRepository<User, Integer> {

	void deleteUserByLogin(String login);

	/**
	 * Finds a user per login.
	 * 
	 * @param login
	 */
	User findUserByLogin(String login);
}