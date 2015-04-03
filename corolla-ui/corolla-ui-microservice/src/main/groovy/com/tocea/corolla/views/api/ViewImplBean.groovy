/**
 *
 */
package com.tocea.corolla.views.api

import org.springframework.stereotype.Service

import com.tocea.corolla.products.domain.Product
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.widget.treeItem.TreeItem

/**
 * @author sleroy
 *
 */
@Service
public class ViewImplBean implements IViewAPI {

	private List<Product> products= []
	private List<User> users = []
	private List<Role> roles = []


	/* (non-Javadoc)
	 * @see com.tocea.corolla.views.api.IViewAPI#deleteUser(com.tocea.corolla.users.domain.User)
	 */
	@Override
	public void deleteUser(final User _user) {
		//this.userDAO.delete _user
	}

	@Override
	public List<Product> getProducts() {
		//	Lists.newArrayList this.productDAO.findAll()
		return products
	}

	@Override
	public List<Role> getRoles() {

		//Lists.newArrayList this.roleDAO.findAll()
		return []
	}

	@Override
	public List<User> getUsers() {
		//Lists.newArrayList this.userDAO.findAll()
		return []
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
