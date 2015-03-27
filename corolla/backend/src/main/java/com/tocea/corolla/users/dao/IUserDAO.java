/**
 *
 */
package com.tocea.corolla.users.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.tocea.corolla.users.domain.User;

/**
 * This interface defines the CRUD to manipulate members.
 * @author sleroy
 *
 *
 */

public interface IUserDAO extends PagingAndSortingRepository<User, Integer> {


	/**
	 * Finds a user per login.
	 * @param login
	 */
	User findUserByLogin(String login);

}
