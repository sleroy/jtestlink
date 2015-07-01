/**
 *
 */
package com.tocea.corolla

import groovy.transform.AutoClone;
import groovy.util.logging.Slf4j;

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.commands.AddNewComponentToApplicationCommand
import com.tocea.corolla.products.dao.IApplicationDAO
import com.tocea.corolla.products.dao.IComponentDAO
import com.tocea.corolla.products.dao.IComponentTypeDAO
import com.tocea.corolla.products.domain.Application
import com.tocea.corolla.products.domain.ApplicationStatus
import com.tocea.corolla.products.domain.Component
import com.tocea.corolla.products.domain.ComponentType
import com.tocea.corolla.users.commands.CreateRoleCommand
import com.tocea.corolla.users.commands.CreateUserCommand
import com.tocea.corolla.users.commands.EditUserCommand
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.dao.IUserDAO
import com.tocea.corolla.users.dao.IUserGroupDAO
import com.tocea.corolla.users.domain.Permission
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.users.domain.UserGroup

/**
 * @author sleroy
 *
 */
@org.springframework.stereotype.Component
@Slf4j
public class DemoDataBean {

	static final String ADMIN_USERS = ADMIN_USERS

	@Autowired
	def IRoleDAO					roleDAO

	@Autowired
	def IUserDAO					userDAO
	
	@Autowired
	def IUserGroupDAO				groupDAO

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
		
		/*
		 * Admin role
		 */
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
		this.gate.dispatch new CreateRoleCommand(roleAdmin)

		/*
		 * Roles
		 */
		final Role roleGuest = this.newRole("Guest", "Guest", [], true)
		final Role roleTester = this.newRole("Tester", "Tester", [Permission.REST])
		final Role roleTestManager = this.newRole("Test manager", "Test Manager", [Permission.REST])
		final Role roleApplicationManager = this.newRole(	"Application manager",
				"Application manager", [Permission.REST])

		/*
		 * Users
		 */
		def jsnow = this.newUser(	"John", "Snow", "john.snow@email.com", "jsnow",
				"password", roleAdmin)
		def scarreau = this.newUser(	"Sébastien", "Carreau", "sebastien.carreau@tocea.com", "scarreau",
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
		
		/*
		 * User Groups
		 */
		def developers = this.newGroup("developers", [jsnow.id, scarreau.id])
				
				
		/*final Application corollaProduct = this.newApplication("COROLLA",	"Corolla",
				"<b>Corolla</b> is a tool to manage softare requirements....")

		backendComponentType = newTypeOfComponent("Backend")
		restComponentType = newTypeOfComponent("Rest")
		screenComponentType = newTypeOfComponent("Screen")
		funcDomainComponentType = newTypeOfComponent("FunctionalDomain")

		def userLayer = newClassicComponent(funcDomainComponentType, corollaProduct, null, "Gestion des utilisateurs")
		def roleLayer = newComponent(funcDomainComponentType, corollaProduct, null, "Gestion des roles")
		def applicationLayer = newComponent(funcDomainComponentType, corollaProduct, null, "Gestion des applications")
		def componentLayer = newComponent(funcDomainComponentType, corollaProduct, applicationLayer, "Gestion des composants")
		def componentTypeLayer = newComponent(funcDomainComponentType, corollaProduct, null, "Gestion des types de composants")
		def exigencesLayer = newComponent(funcDomainComponentType, corollaProduct, null, "Gestion des exigences")
		def scenariosTestsLayer = newComponent(funcDomainComponentType, corollaProduct, null, "Gestion des scénarios de tests")
		def casTestsLayer = newComponent(funcDomainComponentType, corollaProduct, scenariosTestsLayer, "Gestion des cas de tests")
		def generationRapportsLayer = newComponent(funcDomainComponentType, corollaProduct, null, "Génération de rapports")
		def gestiondesCampagnesLayer = newComponent(funcDomainComponentType, corollaProduct, null, "Gestion des campagnes de tests")*/
	}

	def Component newClassicComponent(
			final ComponentType funcArchiType,
			final Application komeaProduct,
			final Component _parentComponent,
			final String _funcName) {
		def funcLayer = newComponent(funcDomainComponentType, komeaProduct, _parentComponent, _funcName)
		def restComponent = newComponent(funcDomainComponentType, komeaProduct, funcLayer, _funcName)
		def serviceComponent = newComponent(funcDomainComponentType, komeaProduct, funcLayer, _funcName)
		def screenComponent = newComponent(funcDomainComponentType, komeaProduct, funcLayer, _funcName)
		return funcLayer
	}

	def ComponentType backendComponentType
	def ComponentType restComponentType
	def ComponentType screenComponentType
	def ComponentType funcDomainComponentType


	def ComponentType newTypeOfComponent(
			final String _architectureName) {
		final ComponentType productComponentType = new ComponentType()
		productComponentType.setName(_architectureName)
		productComponentType.setDescription(_architectureName)
		this.productArchitectureTypeDAO.save(productComponentType)
		return productComponentType
	}

	def Component newComponent(
			final ComponentType funcArchiType,
			final Application komeaProduct,
			final Component _parentComponent,
			final String _funcName) {
		final AddNewComponentToApplicationCommand command = new AddNewComponentToApplicationCommand()
		command.setComponentTypeID(funcArchiType.getId())
		command.setProductID(komeaProduct.getId())
		if (_parentComponent != null) {
			command.setParentComponentID(_parentComponent.getId())
		}
		command.setName(_funcName)
		command.setDescription(_funcName)
		this.gate.dispatch command
	}


	Application newApplication(String _key, final String _name, final String _description) {
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
		this.gate.dispatch new CreateRoleCommand(role)
		log.info("new role created [_id:"+role.getId()+"]");
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
		this.gate.dispatch new CreateRoleCommand(role)
		log.info("new role created [_id:"+role.getId()+"]");
		return role
	}

	public User newUser(final String _firstName, final String _lastName,
			final String _email, final String _login,
			final String _password, final Role _rolePO) {
		final User user = new User()
		user.with {
			firstName = _firstName
			lastName = _lastName
			email =_email
			login =_login
			password = this.passwordEncoder.encode _password
			roleId = _rolePO.id
		}
		this.gate.dispatch new CreateUserCommand(user)
		user.active = true
		this.gate.dispatch new EditUserCommand(user)
		log.info("new user created [_id:"+user.getId()+"]");
		return user
	}
	
	public UserGroup newGroup(final String name, List<String> userIds) {
		
		def group = new UserGroup();
		group.setName(name)
		group.setUserIds(userIds)
		
		groupDAO.save group
		
		return group
		
	}
	
	@PreDestroy
	public void destroy() {
		
		userDAO.deleteAll()
		roleDAO.deleteAll()
		groupDAO.deleteAll()
		
	}
	
}
