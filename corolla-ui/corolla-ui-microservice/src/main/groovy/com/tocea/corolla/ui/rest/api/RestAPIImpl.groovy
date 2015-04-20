/**
 *
 */
package com.tocea.corolla.ui.rest.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.google.common.collect.Lists
import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.dao.IProductDAO
import com.tocea.corolla.products.domain.Product
import com.tocea.corolla.ui.widgets.treeItem.TreeItem
import com.tocea.corolla.users.commands.CreateUserCommand
import com.tocea.corolla.users.commands.DeleteUserCommand
import com.tocea.corolla.users.commands.EditUserCommand
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.dao.IUserDAO
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User

/**
 * @author sleroy
 *
 */
@Service
public class RestAPIImpl implements IRestAPI {
	@Autowired
	def IUserDAO userDAO

	@Autowired
	def IRoleDAO roleDAO

	@Autowired
	def IProductDAO productDAO



	@Autowired
	private Gate gate

	private List<Product> products= []


	/* (non-Javadoc)
	 * @see com.tocea.corolla.ui.rest.api.IRestAPI#deleteUser(com.tocea.corolla.ui.users.domain.User)
	 */
	@Override
	public void deleteUser(final User _user) {
		gate.dispatch new DeleteUserCommand(_user.getLogin())
	}

	@Override
	public List<Product> getProducts() {
		Lists.newArrayList(this.productDAO.findAll())
	}

	@Override
	public List<Role> getRoles() {

		Lists.newArrayList(this.roleDAO.findAll())
	}

	@Override
	public List<User> getUsers() {
		Lists.newArrayList(this.userDAO.findAll())
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

	/* (non-Javadoc)
	 * @see com.tocea.corolla.ui.rest.api.IRestAPI#saveUser(com.tocea.corolla.users.domain.User)
	 */

	@Override
	public void saveUser(User _user) {
		if (_user.getId() == null) {
			gate.dispatch new CreateUserCommand(_user)
		} else {
			gate.dispatch new EditUserCommand(_user)
		}
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.ui.rest.api.IRestAPI#existLogin(java.lang.String)
	 */
	@Override
	public boolean existLogin(String _loginName) {

		return userDAO.findUserByLogin(_loginName) != null
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.ui.rest.api.IRestAPI#getDefaultRole()
	 */
	@Override
	public Role getDefaultRole() {

		return this.roleDAO.defaultRole
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.ui.rest.api.IRestAPI#getRole(java.lang.Integer)
	 */
	@Override
	public Role getRole(Integer _primaryKey) {

		return roleDAO.findOne(_primaryKey)
	}

	/* (non-Javadoc)
	 * @see com.tocea.corolla.ui.rest.api.IRestAPI#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUserByLogin(String _login) {
		gate.dispatch new DeleteUserCommand(_login)
	}
}