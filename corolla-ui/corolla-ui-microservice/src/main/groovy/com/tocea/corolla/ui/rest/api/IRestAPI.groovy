/**
 *
 */
package com.tocea.corolla.ui.rest.api

import com.tocea.corolla.products.domain.Product
import com.tocea.corolla.ui.widgets.treeItem.TreeItem
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User

/**
 * @author sleroy
 *
 */
public interface IRestAPI {
	/**
	 * Deletesanuser.
	 */
	void deleteUser(User _user)

	/**
	 * Returns the list of products.
	 * @return the list of products.
	 */
	List<Product> getProducts()

	/**
	 * Returns the list of roles
	 * @return the roles.
	 */
	List<Role> getRoles()

	/**
	 * Returns the list of users.
	 * @return the users
	 */
	List<User> getUsers()

	User findUser(Integer id)

	List<TreeItem> getProductRoots()

	/**
	 * Creates or edit an user.
	 * @param _user the user
	 */
	void saveUser(User _user)

	boolean existLogin(String loginName)
}
