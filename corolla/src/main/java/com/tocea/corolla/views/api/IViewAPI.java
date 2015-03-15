/**
 *
 */
package com.tocea.corolla.views.api;

import java.util.List;

import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.users.domain.User;

/**
 * @author sleroy
 *
 */
public interface IViewAPI {
	/**
	 * Returns the list of products.
	 * @return the list of products.
	 */
	List<Product> getProducts();

	/**
	 * Returns the list of users.
	 * @return
	 */
	List<User> getUsers();
}
