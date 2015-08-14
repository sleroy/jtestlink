package com.tocea.corolla.tests.utils;

import java.util.List;

import javax.annotation.PostConstruct;

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
import com.tocea.corolla.users.domain.Permission;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;

@Service
public class AuthUserService {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IRoleDAO roleDAO;
	
	@Autowired
	private Gate gate;
	
	@PostConstruct
	public void setUp() {
		
		if (roleDAO.getDefaultRole() == null) {
			
			Role role = new Role();
			role.setName("DEFAULT");
			role.setDefaultRole(true);
			role.setPermissions("");
			
			gate.dispatch(new CreateRoleCommand(role));
		}

	}
	
	public AuthUser projectManager(Project project) {
		
		Role managerRole = new Role();
		managerRole.setName("MANAGER");
		managerRole.setPermissions(Permission.PROJECT_MANAGEMENT);

		createRoleIfNecessary(managerRole);
		
		User managerUser = new User();
		managerUser.setLogin("manager");
		managerUser.setPassword("pass");
		managerUser.setEmail("manager@corolla.com");
		
		createUserIfNecessary(managerUser);
		
		List<String> roleIds = Lists.newArrayList(managerRole.getId());
		
		gate.dispatch(new CreateProjectPermissionCommand(project, managerUser, roleIds));
		
		return new AuthUser(managerUser, managerRole);
	}
	
	private void createUserIfNecessary(User user) {
		
		if (userDAO.findUserByLogin(user.getLogin()) == null) {
			
			gate.dispatch(new CreateUserCommand(user));
		}
	}
	
	private void createRoleIfNecessary(Role role) {
		
		if (roleDAO.findRoleByName(role.getName()) == null) {
			
			gate.dispatch(new CreateRoleCommand(role));
		}
		
	}
	
}
