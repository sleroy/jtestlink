/**
 *
 */
package com.tocea.corolla.views.api

import com.tocea.corolla.products.domain.Product
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.widget.treeItem.TreeItem

/**
 * @author sleroy
 *
 */
public interface IViewAPI {
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
}
