package com.tocea.corolla.tests.utils;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.CreateProjectPermissionCommand;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.ui.security.AuthUser;
import com.tocea.corolla.users.commands.CreateRoleCommand;
import com.tocea.corolla.users.commands.CreateUserCommand;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.permissions.Permissions;

@Service
public class AuthUserService {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IRoleDAO roleDAO;
	
	@Autowired
	private Gate gate;
	
	public AuthUser projectManager(Project project) {
		
		Role managerRole = new Role();
		managerRole.setName("Project Manager");
		managerRole.setPermissions(Permissions.PROJECT_EDIT+", "+Permissions.PROJECT_READ);

		createRoleIfNecessary(managerRole);
		
		User managerUser = new User();
		managerUser.setLogin("project_manager");
		managerUser.setPassword("pass");
		managerUser.setEmail("manager@corolla.com");
		
		createUserIfNecessary(managerUser);
		
		List<String> roleIds = Lists.newArrayList(managerRole.getId());
		
		gate.dispatch(new CreateProjectPermissionCommand(project, managerUser, roleIds));
		
		return new AuthUser(managerUser, managerRole);
	}
	
	public AuthUser projectUser(Project project) {
		
		return projectUser(Lists.newArrayList(project));		
	}
	
	public AuthUser projectUser(Collection<Project> projects) {
		
		Role managerRole = new Role();
		managerRole.setName("Project User");
		managerRole.setPermissions(Permissions.PROJECT_READ);

		createRoleIfNecessary(managerRole);
		
		User managerUser = new User();
		managerUser.setLogin("project_user");
		managerUser.setPassword("pass");
		managerUser.setEmail("manager@corolla.com");
		
		createUserIfNecessary(managerUser);
		
		List<String> roleIds = Lists.newArrayList(managerRole.getId());
		
		for(Project project : projects) {
			gate.dispatch(new CreateProjectPermissionCommand(project, managerUser, roleIds));
		}
		
		return new AuthUser(managerUser, managerRole);		
	}
	
	public AuthUser projectCreator() {
		
		Role role = new Role();
		role.setName("Project Creator");
		role.setPermissions(Permissions.PROJECT_CREATE);
		
		createRoleIfNecessary(role);
		
		User user = new User();
		user.setLogin("portfolio_manager");
		user.setPassword("pass");
		user.setEmail("simple@corolla.com");
		user.setRole(role);
		
		createUserIfNecessary(user);
		
		return new AuthUser(user, role);
		
	}
	
	public AuthUser projectDestroyer() {
		
		Role role = new Role();
		role.setName("Project Creator");
		role.setPermissions(Permissions.PROJECT_DELETE);
		
		createRoleIfNecessary(role);
		
		User user = new User();
		user.setLogin("portfolio_manager");
		user.setPassword("pass");
		user.setEmail("simple@corolla.com");
		user.setRole(role);
		
		createUserIfNecessary(user);
		
		return new AuthUser(user, role);
		
	}
	
	public AuthUser portfolioManager() {
		
		Role role = new Role();
		role.setName("Portfolio Manager");
		role.setPermissions(Permissions.PORTFOLIO_READ+", "+Permissions.PORTFOLIO_WRITE);
		
		createRoleIfNecessary(role);
		
		User user = new User();
		user.setLogin("portfolio_manager");
		user.setPassword("pass");
		user.setEmail("simple@corolla.com");
		user.setRole(role);
		
		createUserIfNecessary(user);
		
		return new AuthUser(user, role);
	}
	
	public AuthUser portfolioUser() {
		
		Role role = new Role();
		role.setName("Portfolio User");
		role.setPermissions(Permissions.PORTFOLIO_READ);
		
		createRoleIfNecessary(role);
		
		User user = new User();
		user.setLogin("portfolio_manager");
		user.setPassword("pass");
		user.setEmail("simple@corolla.com");
		user.setRole(role);
		
		createUserIfNecessary(user);
		
		return new AuthUser(user, role);	
	}
	
	public AuthUser requirementsUser(Project project) {
		
		Role managerRole = new Role();
		managerRole.setName("Project User");
		managerRole.setPermissions(Permissions.REQUIREMENTS_READ);

		createRoleIfNecessary(managerRole);
		
		User managerUser = new User();
		managerUser.setLogin("project_user");
		managerUser.setPassword("pass");
		managerUser.setEmail("manager@corolla.com");
		
		createUserIfNecessary(managerUser);
		
		List<String> roleIds = Lists.newArrayList(managerRole.getId());
		
		gate.dispatch(new CreateProjectPermissionCommand(project, managerUser, roleIds));
		
		return new AuthUser(managerUser, managerRole);
		
	}
	
	public AuthUser requirementsManager(Project project) {
		
		Role managerRole = new Role();
		managerRole.setName("Project User");
		managerRole.setPermissions(Permissions.REQUIREMENTS_WRITE);

		createRoleIfNecessary(managerRole);
		
		User managerUser = new User();
		managerUser.setLogin("project_user");
		managerUser.setPassword("pass");
		managerUser.setEmail("manager@corolla.com");
		
		createUserIfNecessary(managerUser);
		
		List<String> roleIds = Lists.newArrayList(managerRole.getId());
		
		gate.dispatch(new CreateProjectPermissionCommand(project, managerUser, roleIds));
		
		return new AuthUser(managerUser, managerRole);
		
	}
	
	public AuthUser basicUser() {
		
		Role basicRole = new Role();
		basicRole.setName("Basic User");
		basicRole.setPermissions("");
		
		createRoleIfNecessary(basicRole);
		
		User basicUser = new User();
		basicUser.setLogin("simple");
		basicUser.setPassword("pass");
		basicUser.setEmail("simple@corolla.com");
		
		createUserIfNecessary(basicUser);
		
		return new AuthUser(basicUser, basicRole);
	}
	
	public AuthUser admin() {
		
		Role role = new Role();
		role.setName("Admin");
		role.setPermissions(Permissions.ADMIN);
		
		createRoleIfNecessary(role);
		
		User user = new User();
		user.setLogin("admin");
		user.setPassword("pass");
		user.setEmail("simple@corolla.com");
		user.setRole(role);
		
		createUserIfNecessary(user);
		
		return new AuthUser(user, role);	
		
	}
	
	private void createUserIfNecessary(User user) {
		
		if (userDAO.findUserByLogin(user.getLogin()) == null) {
			
			if (roleDAO.getDefaultRole() == null) {
				
				Role role = new Role();
				role.setName("DEFAULT");
				role.setDefaultRole(true);
				role.setPermissions("");
				
				gate.dispatch(new CreateRoleCommand(role));
			}
			
			gate.dispatch(new CreateUserCommand(user));
		}
	}
	
	private void createRoleIfNecessary(Role role) {
		
		if (roleDAO.findRoleByName(role.getName()) == null) {
			
			gate.dispatch(new CreateRoleCommand(role));
		}
		
	}
	
}
