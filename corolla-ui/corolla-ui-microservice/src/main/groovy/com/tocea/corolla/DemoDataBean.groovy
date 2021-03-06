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
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

import com.google.common.base.Function
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.portfolio.commands.CreatePortfolioFolderNodeCommand;
import com.tocea.corolla.portfolio.commands.CreateProjectNodeCommand
import com.tocea.corolla.portfolio.commands.MovePortfolioNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO
import com.tocea.corolla.portfolio.domain.Portfolio
import com.tocea.corolla.portfolio.domain.ProjectNode
import com.tocea.corolla.portfolio.utils.PortfolioUtils;
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
import com.tocea.corolla.requirements.trees.commands.CreateRequirementFolderNodeCommand;
import com.tocea.corolla.requirements.trees.commands.MoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.trees.commands.CreateFolderNodeTypeCommand
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO
import com.tocea.corolla.trees.domain.FolderNode
import com.tocea.corolla.trees.domain.FolderNodeType
import com.tocea.corolla.trees.domain.TreeNode
import com.tocea.corolla.trees.utils.TreeNodeUtils;
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
@Profile("demo")
@Component
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
	def IFolderNodeTypeDAO			folderNodeTypeDAO

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
			Permission.REST,
			Permission.PROJECT_WRITE,
			Permission.PROJECT_READ,
			Permission.PORTFOLIO_MANAGEMENT]
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
		 * Folder Node Types
		 */
		def basicFolder = this.gate.dispatch(new CreateFolderNodeTypeCommand(new FolderNodeType("Basic Folder", "http://icons.iconarchive.com/icons/uriy1966/steel-system/24/Folder-icon.png")))
		def pluginFolder = this.gate.dispatch(new CreateFolderNodeTypeCommand(new FolderNodeType("Plugins", "http://icons.iconarchive.com/icons/elegantthemes/beautiful-flat-one-color/24/plugin-icon.png")))
		
		/**
		 * Project Statuses
		 */
		def statusActive = this.newProjectStatus("Active")
		
		/*
		 * Portfolio
		 */
		def corolla = this.saveProject(new Project(
				key: 'corolla', 
				name: 'Corolla', 
				description: 'A Java Coffee Maker',
				statusId: statusActive.id,
				image: new URL("http://lorempixel.com/24/24/")
		))		
		corolla.description = 'Corolla is a tool to manage software requirements'
		this.editProject(corolla)
		
		def portfolio = portfolioDAO.find()
		def corollaFolderNode = this.gate.dispatch new CreatePortfolioFolderNodeCommand("Corolla-Project", basicFolder, null)
		this.gate.dispatch new MovePortfolioNodeCommand(1, corollaFolderNode.id)
		
		def komeaFolderNode = this.gate.dispatch new CreatePortfolioFolderNodeCommand("Komea", basicFolder, null)			
		
		def komea = this.gate.dispatch new CreateProjectCommand(new Project(
				key: 'komea', 
				name: 'Komea Dashboard', 
				description: 'Tool for measuring and managing key performance indicators in a software factory',
				statusId: statusActive.id
		), komeaFolderNode.id)
		
		def komeaRedmine = this.gate.dispatch new CreateProjectCommand(new Project(
				key: 'komea-connector-redmine', 
				name: 'Komea Redmine Connector', 
				description: 'Redmine connector for Komea',
				statusId: statusActive.id
		), komeaFolderNode.id)
		
		def komeaSvn =this.gate.dispatch new CreateProjectCommand(new Project(
				key: 'komea-connector-svn', 
				name: 'Komea SVN Connector', 
				description: 'SVN connector for Komea',
				statusId: statusActive.id
		), komeaFolderNode.id)
		
		
//		portfolio = portfolioDAO.find();
//		for(int i=1; i<=6000; i++) {			
//			def node = new TextNode(i.toString())
//			portfolio = this.gate.dispatch(new CreateTreeNodeCommand(portfolio, node, null))
//		}
//		portfolioDAO.save(portfolio)
		
//		portfolio = portfolioDAO.find();
//		def parentID = null
//		for(int i=1; i<=6000; i++) {			
//			def node = new TextNode(i.toString())
//			if (i%60 == 0) {
//				parentID = TreeNodeUtils.getMaxNodeId(portfolio.nodes)
//			}
//			portfolio = this.gate.dispatch(new CreateTreeNodeCommand(portfolio, node, parentID))
//		}
//		portfolioDAO.save(portfolio)
		
//		def parentID = null
//		for(int i=1; i<=6000; i++) {
//			if (i% 60 == 0) {
//				portfolio = portfolioDAO.find();
//				parentID = TreeNodeUtils.getMaxNodeId(portfolio.nodes)
//			}
//			this.gate.dispatch new CreateProjectCommand(new Project(
//					key: 'corolla'+i, 
//					name: 'Corolla '+i, 
//					description: 'Whatever',
//					statusId: statusActive.id
//			), parentID)
//		}

		/*
		 * Branches
		 */
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
		
		this.newRequirementTextNode(masterBranch, null, "USER MANAGEMENT", basicFolder)
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
			password = _password
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
	
	public void newRequirementTextNode(branch, parentID, text, type) {
		
		this.gate.dispatch new CreateRequirementFolderNodeCommand(branch, parentID, text, type)
		
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
		
		folderNodeTypeDAO.deleteAll()
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