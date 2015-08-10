package com.tocea.corolla.products.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.CreateProjectBranchCommand;
import com.tocea.corolla.products.commands.CreateProjectCommand;
import com.tocea.corolla.products.commands.CreateProjectStatusCommand;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.ui.AbstractSpringTest;
import com.tocea.corolla.ui.security.AuthUser;
import com.tocea.corolla.users.domain.Permission;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;

public class ProjectRestControllerTest extends AbstractSpringTest {

	private static final String PROJECTS_URL 	= "/rest/projects/";
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	private MockMvc mvc;
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired
	private Gate gate;
	
	private Role basicRole;	
	private User basicUser;
	private Role managerRole;
	private User managerUser;
	
	private ProjectStatus projectStatus;
	private Project existingProject;
	private ProjectBranch masterBranch;
	private ProjectBranch devBranch;
	
	
	@Before
	public void setUp() {
		
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain)
				.build();
		
		basicRole = new Role();
		basicRole.setName("BASIC");
		basicRole.setPermissions("");
		
		basicUser = new User();
		basicUser.setLogin("simple");
		basicUser.setPassword("pass");
		basicUser.setEmail("simple@corolla.com");
		
		managerRole = new Role();
		managerRole.setName("MANAGER");
		managerRole.setPermissions(Permission.PROJECT_MANAGEMENT);
		
		managerUser = new User();
		managerUser.setLogin("manager");
		managerUser.setPassword("pass");
		managerUser.setEmail("manager@corolla.com");
		
		projectStatus = new ProjectStatus();
		projectStatus.setName("Active");
		projectStatus.setClosed(false);
		
		gate.dispatch(new CreateProjectStatusCommand(projectStatus));
		
		existingProject = new Project();
		existingProject.setKey("COROLLA_TEST");
		existingProject.setName("Corolla");
		existingProject.setStatusId(projectStatus.getId());
		
		gate.dispatch(new CreateProjectCommand(existingProject));
		
		masterBranch = branchDAO.findDefaultBranch(existingProject.getId());	
		devBranch = gate.dispatch(new CreateProjectBranchCommand("Dev", existingProject));
		
	}
	
	private String buildDeleteBranchURL(Project project, ProjectBranch branch) {
		
		return
				new StringBuilder(PROJECTS_URL)
				.append(project.getKey())
				.append("/branches/")
				.append(branch.getName())
				.append("/delete")
				.toString();
	}
	
	@Test
	public void projectManagerUserShouldDeleteBranch() throws Exception {
		
		mvc
			.perform(
					get(buildDeleteBranchURL(existingProject, devBranch)).
				with(user(new AuthUser(managerUser, managerRole)))
			)
			.andExpect(status().isOk());
			
		assertNull(branchDAO.findOne(devBranch.getId()));
		
	}
	
	@Test
	public void basicUserShouldNotDeleteBranch() throws Exception {
		
		mvc
			.perform(
					get(buildDeleteBranchURL(existingProject, devBranch)).
				with(user(new AuthUser(basicUser, basicRole)))
			)
			.andExpect(status().isForbidden());
			
		assertNotNull(branchDAO.findOne(devBranch.getId()));
		
	}
	
	@Test
	public void shouldNotDeleteDefaultBranch() throws Exception {
		
		mvc
			.perform(
				get(buildDeleteBranchURL(existingProject, masterBranch))
				.with(user(new AuthUser(managerUser, managerRole)))
			)
			.andExpect(status().isNotAcceptable());
		
		assertNotNull(branchDAO.findOne(masterBranch.getId()));
		
	}
}