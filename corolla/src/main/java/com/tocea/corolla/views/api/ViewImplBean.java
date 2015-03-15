/**
 *
 */
package com.tocea.corolla.views.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.User;

/**
 * @author sleroy
 *
 */
@Service
public class ViewImplBean implements IViewAPI {

	@Autowired
	private IUserDAO userDAO;

	/* (non-Javadoc)
	 * @see com.tocea.corolla.views.api.IViewAPI#getProducts()
	 */
	@Override
	public List<Product> getProducts() {
		final ArrayList<Product> newArrayList = Lists.newArrayList();
		final Product prd = new Product();
		prd.setName("Komea");
		prd.setDescription("Komea product");

		final Product prd2 = new Product();
		prd2.setName("Cobos");
		prd2.setDescription("Cobos product");
		newArrayList.add(prd);
		newArrayList.add(prd2);
		return newArrayList				;
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.views.api.IViewAPI#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		return Lists.newArrayList(this.userDAO.findAll());
	}

}
