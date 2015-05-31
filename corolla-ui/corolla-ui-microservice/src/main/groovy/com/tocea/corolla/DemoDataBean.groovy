/**
 *
 */
package com.tocea.corolla

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.commands.AddNewArchitectureToApplicationCommand
import com.tocea.corolla.products.dao.IApplicationDAO
import com.tocea.corolla.products.dao.IComponentDAO
import com.tocea.corolla.products.dao.IComponentTypeDAO
import com.tocea.corolla.products.domain.Application
import com.tocea.corolla.products.domain.ApplicationStatus
import com.tocea.corolla.products.domain.ComponentType
import com.tocea.corolla.users.commands.CreateUserCommand
import com.tocea.corolla.users.commands.EditUserCommand
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.dao.IUserDAO
import com.tocea.corolla.users.domain.Permission
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User

/**
 * @author sleroy
 *
 */
@Component
public class DemoDataBean {

	static final String ADMIN_USERS = ADMIN_USERS

	@Autowired
	def IRoleDAO					roleDAO

	@Autowired
	def IUserDAO					userDAO

	@Autowired
	def IApplicationDAO					applicationDAO

	@Autowired
	def PasswordEncoder			passwordEncoder

	@Autowired
	def IComponentDAO		productArchitectureDAO

	@Autowired
	def IComponentTypeDAO	productArchitectureTypeDAO

	@Autowired
	def Gate						gate

	@SuppressWarnings("nls")
	@PostConstruct
	public void init() throws MalformedURLException {

		final Role roleAdmin = this.newRole("Administrator", "Administrator role", [
			Permission.ADMIN,
			Permission.ADMIN_ROLES,
			Permission.ADMIN_CONFIG,
			Permission.ADMIN_USERS ,
			Permission.REQUIREMENT,
			Permission.APPLICATION,
			Permission.TESTSUITE,
			Permission.TESTCAMP,
			Permission.REST]
		)
		roleAdmin.roleProtected = true
		roleDAO.save roleAdmin

		final Role roleGuest = this.newRole("Guest", "Guest", [], true)
		final Role roleTester = this.newRole("Tester", "Tester", [Permission.REST])
		final Role roleTestManager = this.newRole("Test manager", "Test Manager", [Permission.REST])
		final Role roleApplicationManager = this.newRole(	"Application manager",
				"Application manager", [Permission.REST])

		this.newUser(	"John", "Snow", "john.snow@email.com", "jsnow",
				"password", roleAdmin)
		this.newUser(	"Sébastien", "Carreau", "sebastien.carreau@tocea.com", "scarreau",
				"scarreau", roleAdmin)
		this.newUser(	"Jack", "Pirate", "jack.pirate@email.com", "jpirate",
				"password", roleGuest)
		this.newUser(	"Ichigo", "Kurosaki", "ichigo.kurosaki@email.com",
				"ikurosaki", "password", roleTester)
		this.newUser(	"James", "Bond", "james.bond@mi6.com", "jbond", "007",
				roleTestManager)
		this.newUser(	"Gandalf", "LeGris", "gandalf.legris@lotr.com",
				"gandalf",
				"saroumaneisg..", roleApplicationManager)
		this.newUser(	"Saroumane", "LeBlanch", "saroumane.leblanc@lotr.com",
				"saroumane",
				"fuckSauron..", roleAdmin)
		//		final ComponentType funcArchiType = this.newArchitectureType("Architecture fonctionnelle")
		//		final ComponentType techArchiType = this.newArchitectureType("Architecture technique")
		//		final ComponentType componentArchitectureType = this.newArchitectureType("Composant")
		//		final ComponentType coucheArchiType = this.newArchitectureType("Couche")
		//		final ComponentType functionalityArchiType = this.newArchitectureType("Fonctionnalité")
		//
		final Application komeaProduct = this.newProduct("KOMEA_DASHBOARD",	"Komea Dashboard",
				"<b>komea</b> is a dashboard software to measure ....")
		//
		//		final Component productFuncArchitecture = this.newFunctionality(funcArchiType, komeaProduct, null, "Architecture fonctionnelle")
		//
		//		final Component productTechArchitecture = this.newFunctionality(techArchiType, komeaProduct, null, "Architecture technique")
		//
		//		this.newFunctionality(	functionalityArchiType, komeaProduct,
		//				productFuncArchitecture, "Gestion des CRONS")
		//		this.newFunctionality(	functionalityArchiType, komeaProduct,
		//				productFuncArchitecture,
		//				"Moteur de statistiques")
		//		this.newFunctionality(	functionalityArchiType, komeaProduct,
		//				productFuncArchitecture, "Execution des KPIS")
		//		this.newFunctionality(	functionalityArchiType, komeaProduct,
		//				productFuncArchitecture,
		//				"Moteur de statistiques")
		//
		//		this.newFunctionality(	coucheArchiType, komeaProduct,
		//				productTechArchitecture, "Portail Liferay")
		//		final Component admin = this.newFunctionality(	coucheArchiType,
		//				komeaProduct,
		//				productTechArchitecture,
		//				"IHM Administration")
		//		this.newFunctionality(	componentArchitectureType, komeaProduct, admin,
		//				"Gestion des KPIS")
		//		this.newFunctionality(	componentArchitectureType, komeaProduct, admin,
		//				"Gestion des personnes")
		//		this.newFunctionality(	componentArchitectureType, komeaProduct, admin,
		//				"Gestion des équipes")
		//		this.newFunctionality(	componentArchitectureType, komeaProduct, admin,
		//				"Gestion des départements")
		//		this.newFunctionality(coucheArchiType,
		//				komeaProduct,
		//				productTechArchitecture,
		//				"Backend")
		//		final Component plugins = this.newFunctionality(	coucheArchiType, komeaProduct,
		//				productTechArchitecture, "Plugins")
		//		this.newFunctionality(componentArchitectureType, komeaProduct, plugins, "Plugin Bugzilla")
		//		this.newFunctionality(componentArchitectureType, komeaProduct, plugins, "Plugin Testlink")
		//		this.newFunctionality(	coucheArchiType, komeaProduct,
		//				productTechArchitecture, "Kpis")
		//		this.newFunctionality(	coucheArchiType, komeaProduct,
		//				productTechArchitecture, "Providers")
	}

	/**
	 * @param _architectureName
	 * @return
	 */
	ComponentType newArchitectureType(
			final String _architectureName) {
		final ComponentType productComponentType = new ComponentType()
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
	Component newFunctionality(
			final ComponentType funcArchiType,
			final Application komeaProduct,
			final Component _parentComponent,
			final String _funcName) {
		final AddNewArchitectureToApplicationCommand command = new AddNewArchitectureToApplicationCommand()
		command.setArchitectureTypeID(funcArchiType.getId())
		command.setProductID(komeaProduct.getId())
		if (_parentComponent != null) {
			command.setParentArchitectureID(_parentComponent.getId())
		}
		command.setName(_funcName)
		command.setDescription(_funcName)
		return (Component) this.gate.dispatch(command)
	}


	Application newProduct(String _key, final String _name, final String _description) {
		final Application product = new Application()
		product.with {
			key = _key
			name = _name
			description = _description
			status = ApplicationStatus.ACTIVE
			image = "http://dummyimage.com/50x50&text=" + _key
		}
		this.applicationDAO.save product
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
		role.with {
			name = _roleName
			note = _roleNote
			permissions = ""
			roleProtected = false
		}
		this.roleDAO.save role
		return role
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
	public Role newRole(final String _roleName, final String _roleNote, List<String> _roles, boolean _defaultRole=false) {
		final Role role = new Role()
		role.with {
			name = _roleName
			note = _roleNote
			permissions = _roles.join(", ")
			defaultRole = _defaultRole
			roleProtected = false
		}
		this.roleDAO.save role
		return role
	}

	public User newUser(final String _string, final String _string2,
			final String _string3, final String _string4,
			final String _password, final Role _rolePO) {
		final User user = new User()
		user.with {
			firstName = _string
			lastName = _string2
			email =_string3
			login =_string4
			password = this.passwordEncoder.encode _password
			roleId = _rolePO.id
		}
		user = this.gate.dispatch new CreateUserCommand(user)
		user.active = true
		this.gate.dispatch(new EditUserCommand(user))
		return user
	}
}
