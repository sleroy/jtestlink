/**
 *
 */
package com.tocea.corolla

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.commands.AddNewArchitectureToProductCommand
import com.tocea.corolla.products.dao.IProductArchitectureDAO
import com.tocea.corolla.products.dao.IProductArchitectureTypeDAO
import com.tocea.corolla.products.dao.IProductDAO
import com.tocea.corolla.products.domain.Product
import com.tocea.corolla.products.domain.ProductComponent
import com.tocea.corolla.products.domain.ProductComponentType
import com.tocea.corolla.users.api.IPasswordEncoder
import com.tocea.corolla.users.commands.AddNewUserCommand
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.dao.IUserDAO
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User

/**
 * @author sleroy
 *
 */
@Component
class DemoDataBean {

	@Autowired
	IRoleDAO					roleDAO

	@Autowired
	IUserDAO					userDAO

	@Autowired
	IProductDAO					productDAO

	@Autowired
	IPasswordEncoder			passwordEncoder

	@Autowired
	IProductArchitectureDAO		productArchitectureDAO

	@Autowired
	IProductArchitectureTypeDAO	productArchitectureTypeDAO

	@Autowired
	Gate						gate

	@SuppressWarnings("nls")
	@PostConstruct
	public void init() throws MalformedURLException {

		final Role rolePO = this.newRole("productOwner", "Product Owner")
		final Role roleTM = this.newRole("testManager", "Test Manager")
		final Role roleTester = this.newRole("tester", "Tester")
		final Role rolePM = this.newRole("projectManager", "Project Manager")
		final Role roleFM = this.newRole(	"functionalManager",
				"Functional Manager")

		this.newUser(	"John", "Snow", "john.snow@email.com", "jsnow",
				"password", rolePO)
		this.newUser(	"Jack", "Pirate", "jack.pirate@email.com", "jpirate",
				"password", roleTM)
		this.newUser(	"Ichigo", "Kurosaki", "ichigo.kurosaki@email.com",
				"ikurosaki", "password", roleTester)
		this.newUser(	"James", "Bond", "james.bond@mi6.com", "jbond", "007",
				rolePM)
		this.newUser(	"Gandalf", "LeGris", "gandalf.legris@lotr.com",
				"gandalf",
				"saroumaneisg..", roleFM)
		this.newUser(	"Saroumane", "LeBlanch", "saroumane.leblanc@lotr.com",
				"saroumane",
				"fuckSauron..", roleFM)

		final ProductComponentType funcArchiType = this.newArchitectureType("Architecture fonctionnelle")
		final ProductComponentType techArchiType = this.newArchitectureType("Architecture technique")
		final ProductComponentType componentArchitectureType = this.newArchitectureType("Composant")
		final ProductComponentType coucheArchiType = this.newArchitectureType("Couche")
		final ProductComponentType functionalityArchiType = this.newArchitectureType("Fonctionnalité")

		final Product komeaProduct = this.newProduct(	"Komea",
				"<b>komea</b> is a dashboard software to measure ....")

		final ProductComponent productFuncArchitecture = this.newFunctionality(funcArchiType, komeaProduct, null, "Architecture fonctionnelle")

		final ProductComponent productTechArchitecture = this.newFunctionality(techArchiType, komeaProduct, null, "Architecture technique")

		this.newFunctionality(	functionalityArchiType, komeaProduct,
				productFuncArchitecture, "Gestion des CRONS")
		this.newFunctionality(	functionalityArchiType, komeaProduct,
				productFuncArchitecture,
				"Moteur de statistiques")
		this.newFunctionality(	functionalityArchiType, komeaProduct,
				productFuncArchitecture, "Execution des KPIS")
		this.newFunctionality(	functionalityArchiType, komeaProduct,
				productFuncArchitecture,
				"Moteur de statistiques")

		this.newFunctionality(	coucheArchiType, komeaProduct,
				productTechArchitecture, "Portail Liferay")
		final ProductComponent admin = this.newFunctionality(	coucheArchiType,
				komeaProduct,
				productTechArchitecture,
				"IHM Administration")
		this.newFunctionality(	componentArchitectureType, komeaProduct, admin,
				"Gestion des KPIS")
		this.newFunctionality(	componentArchitectureType, komeaProduct, admin,
				"Gestion des personnes")
		this.newFunctionality(	componentArchitectureType, komeaProduct, admin,
				"Gestion des équipes")
		this.newFunctionality(	componentArchitectureType, komeaProduct, admin,
				"Gestion des départements")
		this.newFunctionality(coucheArchiType,
				komeaProduct,
				productTechArchitecture,
				"Backend")
		final ProductComponent plugins = this.newFunctionality(	coucheArchiType, komeaProduct,
				productTechArchitecture, "Plugins")
		this.newFunctionality(componentArchitectureType, komeaProduct, plugins, "Plugin Bugzilla")
		this.newFunctionality(componentArchitectureType, komeaProduct, plugins, "Plugin Testlink")
		this.newFunctionality(	coucheArchiType, komeaProduct,
				productTechArchitecture, "Kpis")
		this.newFunctionality(	coucheArchiType, komeaProduct,
				productTechArchitecture, "Providers")
	}

	/**
	 * @param _architectureName
	 * @return
	 */
	ProductComponentType newArchitectureType(
			final String _architectureName) {
		final ProductComponentType productComponentType = new ProductComponentType()
		productComponentType.setName(_architectureName)
		productComponentType.setDescription(_architectureName)
		this.productArchitectureTypeDAO.save(productComponentType)
		return productComponentType
	}

	/**
	 * @param funcArchiType
	 * @param komeaProduct
	 * @param _parentComponent
	 * @param _funcName
	 * @return
	 */
	ProductComponent newFunctionality(
			final ProductComponentType funcArchiType,
			final Product komeaProduct,
			final ProductComponent _parentComponent,
			final String _funcName) {
		final AddNewArchitectureToProductCommand command = new AddNewArchitectureToProductCommand()
		command.setArchitectureTypeID(funcArchiType.getId())
		command.setProductID(komeaProduct.getId())
		if (_parentComponent != null) {
			command.setParentArchitectureID(_parentComponent.getId())
		}
		command.setName(_funcName)
		command.setDescription(_funcName)
		return (ProductComponent) this.gate.dispatch(command)
	}

	/**
	 * @param _string
	 * @param _string2
	 * @return
	 */
	Product newProduct(final String _string, final String _string2) {
		final Product product = new Product()
		product.setName(_string)
		product.setDescription(_string2)
		this.productDAO.save(product)
		return product
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
	Role newRole(final String _roleName, final String _roleNote) {
		final Role role = new Role()
		role.setName(_roleName)
		role.setNote(_roleNote)
		this.roleDAO.save(role)
		return role
	}

	User newUser(final String _string, final String _string2,
			final String _string3, final String _string4,
			final String _password, final Role _rolePO) {
		final User user = new User()
		user.setFirstName(_string)
		user.setLastName(_string2)
		user.setEmail(_string3)
		user.setLogin(_string4)
		user.setPassword(this.passwordEncoder.encodePassword(_password))
		user.setRole_id(_rolePO.getId())
		this.gate.dispatch(new AddNewUserCommand(user))
		return user
	}
}
