/**
 *
 */
package com.tocea.corolla.views.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.google.common.collect.Lists
import com.tocea.corolla.products.dao.IProductDAO
import com.tocea.corolla.products.domain.Product
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.dao.IUserDAO
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.widget.treeItem.TreeItem

/**
 * @author sleroy
 *
 */
@Service
public class ViewImplBean implements IViewAPI {

	@Autowired
	private IUserDAO userDAO

	@Autowired
	private IRoleDAO roleDAO

	@Autowired
	private IProductDAO productDAO

	/* (non-Javadoc)
	 * @see com.tocea.corolla.views.api.IViewAPI#deleteUser(com.tocea.corolla.users.domain.User)
	 */
	@Override
	public void deleteUser(final User _user) {
		this.userDAO.delete _user
	}

	@Override
	public List<Product> getProducts() {
		Lists.newArrayList this.productDAO.findAll()
	}

	@Override
	public List<Role> getRoles() {

		Lists.newArrayList this.roleDAO.findAll()
	}

	@Override
	public List<User> getUsers() {
		Lists.newArrayList this.userDAO.findAll()
	}

	public User findUser(Integer _id) {

		userDAO.findOne _id
	}

	@Override
	public List<TreeItem> getProductRoots() {
		List<TreeItem> res = new ArrayList<>()
		for (Product product in productDAO.findAll()) {
			res.add newProductTreeItem(it)
		}
	}

	def TreeItem newProductTreeItem(Product product) {
		def treeItem = new TreeItem()
		treeItem.with {
			itemName = product.name
			itemID = product.id
			entityType = "PRODUCT"
		}
		return treeItem
	}
}
