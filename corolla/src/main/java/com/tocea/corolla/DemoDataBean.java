/**
 *
 */
package com.tocea.corolla;

import java.net.MalformedURLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.AddNewArchitectureToProductCommand;
import com.tocea.corolla.products.dao.IProductArchitectureDAO;
import com.tocea.corolla.products.dao.IProductArchitectureTypeDAO;
import com.tocea.corolla.products.dao.IProductDAO;
import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.products.domain.ProductArchitectureType;
import com.tocea.corolla.users.api.IPasswordEncoder;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;

/**
 * @author sleroy
 *
 */
@Component
public class DemoDataBean {

	@Autowired
	private IRoleDAO					roleDAO;

	@Autowired
	private IUserDAO					userDAO;

	@Autowired
	private IProductDAO					productDAO;

	@Autowired
	private IPasswordEncoder			passwordEncoder;

	@Autowired
	private IProductArchitectureDAO		productArchitectureDAO;

	@Autowired
	private IProductArchitectureTypeDAO	productArchitectureTypeDAO;


	@Autowired
	private Gate iCommandHandler;


	@SuppressWarnings("nls")
	@PostConstruct
	public void init() throws MalformedURLException {

		final Role rolePO = this.newRole("productOwner", "Product Owner");
		final Role roleTM = this.newRole("testManager", "Test Manager");
		final Role roleTester = this.newRole("tester", "Tester");
		final Role rolePM = this.newRole("projectManager", "Project Manager");
		final Role roleFM = this.newRole(	"functionalManager",
				"Functional Manager");

		this.newUser(	"John", "Snow", "john.snow@email.com", "jsnow",
		             	"password", rolePO);
		this.newUser(	"Jack", "Pirate", "jack.pirate@email.com", "jpirate",
		             	"password", roleTM);
		this.newUser(	"Ichigo", "Kurosaki", "ichigo.kurosaki@email.com",
		             	"ikurosaki", "password", roleTester);
		this.newUser(	"James", "Bond", "james.bond@mi6.com", "jbond", "007",
		             	rolePM);
		this.newUser(	"Gandalf", "LeGris", "gandald.legris@lotr.com",
		             	"gandalf",
		             	"saroumaneisg..", roleFM);

		final ProductArchitectureType funcArchiType = this.newArchitectureType("Architecture fonctionnelle");
		final ProductArchitectureType techArchiType = this.newArchitectureType("Architecture technique");


		final Product newProduct = this.newProduct(	"Komea",
				"<b>komea</b> is a dashboard software to measure ....");

		this.iCommandHandler.dispatch(new AddNewArchitectureToProductCommand(newProduct.getId(), funcArchiType.getId()));
		this.iCommandHandler.dispatch(new AddNewArchitectureToProductCommand(newProduct.getId(), techArchiType.getId()));

	}

	/**
	 * @param _architectureName
	 * @return
	 */
	private ProductArchitectureType newArchitectureType(final String _architectureName) {
		final ProductArchitectureType productArchitectureType = new ProductArchitectureType();
		productArchitectureType.setName(_architectureName);
		productArchitectureType.setDescription(_architectureName);
		this.productArchitectureTypeDAO.save(productArchitectureType);
		return productArchitectureType;

	}

	/**
	 * @param _string
	 * @param _string2
	 * @return
	 */
	private Product newProduct(final String _string, final String _string2) {
		final Product product = new Product();
		product.setName(_string);
		product.setDescription(_string2);
		this.productDAO.save(product);
		return product;

	}

	/**
	 * Creates a new role.
	 *
	 * @param _roleName
	 *            the role name
	 * @param _roleNote
	 *            the role note
	 * @return the new role
	 */
	private Role newRole(final String _roleName, final String _roleNote) {
		final Role role = new Role();
		role.setName(_roleName);
		role.setNote(_roleNote);
		this.roleDAO.save(role);
		return role;
	}

	private User newUser(final String _string, final String _string2,
			final String _string3, final String _string4,
			final String _password, final Role _rolePO) {
		final User user = new User();
		user.setFirstName(_string);
		user.setLastName(_string2);
		user.setEmail(_string3);
		user.setLogin(_string4);
		user.setPassword(this.passwordEncoder.encodePassword(_password));
		user.setRole_id(_rolePO.getId());
		this.userDAO.save(user);
		return user;
	}
}
