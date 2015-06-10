/**
 *
 */
package com.tocea.corolla.users.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
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
	
	/**
	 * Finds the users who have a specific role
	 * @param roleId
	 * @return
	 */
	List<User> findByRoleId(Integer roleId);
	
}
