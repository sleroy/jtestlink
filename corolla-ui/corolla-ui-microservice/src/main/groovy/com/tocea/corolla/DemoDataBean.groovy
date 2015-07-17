/**
 *
 */
package com.tocea.corolla

import groovy.transform.AutoClone;
import groovy.util.logging.Slf4j;

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy;

import org.javers.core.Javers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

import com.google.common.base.Function
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.portfolio.dao.IPortfolioDAO
import com.tocea.corolla.portfolio.domain.Portfolio
import com.tocea.corolla.portfolio.domain.ProjectNode
import com.tocea.corolla.products.commands.CreateProjectBranchCommand
import com.tocea.corolla.products.commands.CreateProjectCommand
import com.tocea.corolla.products.commands.CreateProjectStatusCommand
import com.tocea.corolla.products.commands.EditProjectCommand
import com.tocea.corolla.products.dao.IProjectBranchDAO
import com.tocea.corolla.products.dao.IProjectDAO
import com.tocea.corolla.products.dao.IProjectStatusDAO
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.requirements.commands.CreateRequirementCommand
import com.tocea.corolla.requirements.commands.EditRequirementCommand
import com.tocea.corolla.requirements.commands.RestoreRequirementStateCommand
import com.tocea.corolla.requirements.dao.IRequirementDAO
import com.tocea.corolla.requirements.domain.Requirement
import com.tocea.corolla.requirements.trees.commands.CreateRequirementTextNodeCommand;
import com.tocea.corolla.requirements.trees.commands.MoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.trees.domain.TreeNode
import com.tocea.corolla.users.commands.CreateRoleCommand
import com.tocea.corolla.users.commands.CreateUserCommand
import com.tocea.corolla.users.commands.CreateUserGroupCommand
import com.tocea.corolla.users.commands.EditRoleCommand
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
	def IProjectDAO					projectDAO
	
	@Autowired
	def IProjectStatusDAO			projectStatusDAO
	
	@Autowired
	def IProjectBranchDAO			projectBranchDAO
	
	@Autowired
	def IRequirementDAO				requirementDAO
	
	@Autowired
	def IRequirementsTreeDAO		requirementsTreeDAO

	@Autowired
	def PasswordEncoder				passwordEncoder
	
	@Autowired
	def IRevisionService			revisionService
	
	@Autowired
	def IPortfolioDAO				portfolioDAO

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
		this.gate.dispatch new EditRoleCommand(roleAdmin)

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
		def developers = this.newGroup("developers", [jsnow, scarreau])
				
		
		/**
		 * Project Statuses
		 */
		def statusActive = this.newProjectStatus("Active")
		
		/*
		 * Projects
		 */
		def corolla = this.saveProject(new Project(
				key: 'corolla', 
				name: 'Corolla', 
				description: 'A Java Coffee Maker',
				statusId: statusActive.id
		))		
		corolla.description = 'Corolla is a tool to manage software requirements'
		this.editProject(corolla)
		
		def masterBranch = projectBranchDAO.findByNameAndProjectId("Master", corolla.id)

		/*
		 * Requirements
		 */
		def req_addUser = this.newRequirement(new Requirement(
				key: 'ADD_USER',
				projectBranchId: masterBranch.id,
				name: 'Add a user',
				description: 'Create a new user to Corolla from the administration panel'
		))
		def req_editUser = this.newRequirement(new Requirement(
			key: 'EDIT_USER',
			projectBranchId: masterBranch.id,
			name: 'Edit a user profile',
			description: 'Edit a user\'s profile from the administration panel'
		))
		def req_deleteUser = this.newRequirement(new Requirement(
			key: 'DELETE_USER',
			projectBranchId: masterBranch.id,
			name: 'Delete a user',
			description: "Delete a user from the administration panel"
		))
		
		/*
		 * Requirements Tree
		 */
		def tree = requirementsTreeDAO.findByBranchId(masterBranch.id)
		
		this.newRequirementTextNode(masterBranch, null, "USER MANAGEMENT")
		this.moveRequirementNode(masterBranch, this.findRequirementTreeNode(tree, req_addUser.id).id, 4)
		this.moveRequirementNode(masterBranch, this.findRequirementTreeNode(tree, req_editUser.id).id, 4)
		this.moveRequirementNode(masterBranch, this.findRequirementTreeNode(tree, req_deleteUser.id).id, 4)
		
		/**
		 * Create a new Branch derivated from Master
		 */
		def toceaBranch = gate.dispatch new CreateProjectBranchCommand("Tocea", masterBranch);		
		
		/**
		 * Edit a requirement then restore it to its previous state
		 */
		req_addUser = requirementDAO.findByKeyAndProjectBranchId("ADD_USER", masterBranch.id)
		req_addUser.name = "Add an elephant"
		this.gate.dispatch new EditRequirementCommand(req_addUser);		
		def commits = revisionService.getHistory(req_addUser.id, Requirement.class);
		this.gate.dispatch new RestoreRequirementStateCommand(req_addUser.id, commits[1].id);
				
		def portfolio = new Portfolio()
		portfolio.setNodes([new ProjectNode("PR1")])
		portfolioDAO.save(portfolio)
		
		def p = portfolioDAO.find()
		println p.nodes.size()+" nodes in portfolio"
		
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
	
	public UserGroup newGroup(final String name, List<User> users) {
		
		def group = new UserGroup();
		group.setName(name)
		group.setUserIds(users.collect { it.login })
		
		this.gate.dispatch new CreateUserGroupCommand(group);
		log.info("new user group created [_id: {}", group.getId());
		
		return group
		
	}
	
	public Project saveProject(project) {
		
		this.gate.dispatch new CreateProjectCommand(project)
		
		return project
		
	}
	
	public Project editProject(project) {
		
		this.gate.dispatch new EditProjectCommand(project)
		
		return project
	}
	
	public ProjectStatus newProjectStatus(name) {
		
		def status = new ProjectStatus(name: name)
		this.gate.dispatch new CreateProjectStatusCommand(status)
		
		return status
	}
	
	public Requirement newRequirement(requirement) {
		
		this.gate.dispatch new CreateRequirementCommand(requirement)
		
		return requirement
		
	}
	
	public void newRequirementTextNode(branch, parentID, text) {
		
		this.gate.dispatch new CreateRequirementTextNodeCommand(branch, parentID, text)
		
	}
	
	public void moveRequirementNode(branch, nodeID, newParentID) {
		
		this.gate.dispatch new MoveRequirementTreeNodeCommand(branch, nodeID, newParentID)
		
	}
	
	public TreeNode findRequirementTreeNode(tree, requirementID) {
		
		for(TreeNode node : tree.nodes) {
			if (node instanceof RequirementNode && node.requirementId == requirementID) {
				return node
			}
		}
		return null
		
	}
	
	@PreDestroy
	public void destroy() {
		
		requirementsTreeDAO.deleteAll()
		requirementDAO.deleteAll()
		projectBranchDAO.deleteAll()
		projectDAO.deleteAll()	
		userDAO.deleteAll()
		roleDAO.deleteAll()
		groupDAO.deleteAll()
		projectStatusDAO.deleteAll()	
		portfolioDAO.deleteAll()
		
	}
	
}