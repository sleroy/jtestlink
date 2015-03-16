/**
 *
 */
package com.tocea.corolla.views.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tocea.corolla.products.dao.IProductDAO;
import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;

/**
 * @author sleroy
 *
 */
@Service
public class ViewImplBean implements IViewAPI {

	@Autowired
	private IUserDAO userDAO;

	@Autowired
	private IRoleDAO roleDAO;

	@Autowired
	private IProductDAO productDAO;

	/* (non-Javadoc)
	 * @see com.tocea.corolla.views.api.IViewAPI#getProducts()
	 */
	@Override
	public List<Product> getProducts() {
		return Lists.newArrayList(this.productDAO.findAll());
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.views.api.IViewAPI#getRoles()
	 */
	@Override
	public List<Role> getRoles() {

		return Lists.newArrayList(this.roleDAO.findAll());
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.views.api.IViewAPI#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		return Lists.newArrayList(this.userDAO.findAll());
	}

}
