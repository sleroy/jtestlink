package com.tocea.corolla.ui.views.projects;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.CreateProjectCommand;
import com.tocea.corolla.products.commands.CreateProjectPermissionCommand;
import com.tocea.corolla.products.commands.CreateProjectStatusCommand;
import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectPermission;
import com.tocea.corolla.products.domain.ProjectPermission.EntityType;
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.tests.utils.AuthUserService;
import com.tocea.corolla.ui.AbstractSpringTest;
import com.tocea.corolla.ui.security.AuthUser;
import com.tocea.corolla.users.commands.CreateRoleCommand;
import com.tocea.corolla.users.commands.CreateUserCommand;
import com.tocea.corolla.users.commands.CreateUserGroupCommand;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;
import com.tocea.corolla.users.domain.UserGroup;
import com.tocea.corolla.users.permissions.Permissions;

public class ProjectMembersPageControllerTest extends AbstractSpringTest {

	private static final String PROJECTS_URL 			= "/ui/projects/";
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	 
	private MockMvc mvc;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private IProjectPermissionDAO permissionDAO;
	
	@Autowired
	private AuthUserService authService;
	
	private ProjectStatus projectStatus;
	private Project existingProject;
	private User existingUser;
	private UserGroup existingGroup;
	private List<Role> roles;
	
	@Before
	public void setUp() {
		
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain)
				.build();
		
		projectStatus = new ProjectStatus();
		projectStatus.setName("Active");
		projectStatus.setClosed(false);
		
		gate.dispatch(new CreateProjectStatusCommand(projectStatus));
		
		existingProject = new Project();
		existingProject.setKey("COROLLA_TEST");
		existingProject.setName("Corolla");
		existingProject.setStatusId(projectStatus.getId());
		
		gate.dispatch(new CreateProjectCommand(existingProject));
			
		Role role1 = new Role();
		role1.setName("Role1");
		role1.setPermissions(Permissions.REQUIREMENTS_READ);
		role1.setDefaultRole(true);
		
		Role role2 = new Role();
		role2.setName("Role2");
		role2.setPermissions(Permissions.PROJECT_READ);
		
		roles = Lists.newArrayList(role1, role2);
		
		for(Role role : roles) {
			gate.dispatch(new CreateRoleCommand(role));
		}
		
		existingUser = new User();
		existingUser.setLogin("user1");
		existingUser.setFirstName("user_firstname");
		existingUser.setLastName("user_lastname");
		existingUser.setEmail("user1@corolla.com");
		existingUser.setActive(true);
		
		gate.dispatch(new CreateUserCommand(existingUser));
		
		existingGroup = new UserGroup();
		existingGroup.setName("Group1");
		
		gate.dispatch(new CreateUserGroupCommand(existingGroup));
		
	}
	
	private String buildAddFormURL(Project project) {
		
		return
				new StringBuilder(PROJECTS_URL)
				.append(project.getKey())
				.append("/members/add")
				.toString();
		
	}
	
	@Test
	public void testAddUserProjectPermission() throws Exception {
		
		String entityID = existingUser.getId();
		String[] rolesId = { roles.get(0).getId(), roles.get(0).getId() };
			
		AuthUser authUser = authService.projectManager(existingProject);
		
		long nbPermissions = permissionDAO.count();
		
		mvc
		.perform(
				post(buildAddFormURL(existingProject))
				.param("projectId", existingProject.getId())
				.param("entityType", EntityType.USER.toString())
				.param("entityId", entityID)
				.param("roleIds", rolesId)
				.with(user(authUser)))
		.andExpect(status().isFound());
		
		assertEquals(nbPermissions + 1, permissionDAO.count());
		
		ProjectPermission permission = permissionDAO.findByProjectIdAndEntityIdAndEntityType(existingProject.getId(), entityID, EntityType.USER);
		
		assertNotNull(permission);
		assertNotNull(permission.getRoleIds());
		
	}
	
	@Test
	public void testAddGroupProjectPermission() throws Exception {
		
		String entityID = existingGroup.getId();
		String[] rolesId = { roles.get(0).getId(), roles.get(0).getId() };
			
		AuthUser authUser = authService.projectManager(existingProject);
		
		long nbPermissions = permissionDAO.count();
		
		mvc
		.perform(
				post(buildAddFormURL(existingProject))
				.param("projectId", existingProject.getId())
				.param("entityType", EntityType.GROUP.toString())
				.param("entityId", entityID)
				.param("roleIds", rolesId)
				.with(user(authUser)))
		.andExpect(status().isFound());
		
		assertEquals(nbPermissions + 1, permissionDAO.count());
		
		ProjectPermission permission = permissionDAO.findByProjectIdAndEntityIdAndEntityType(existingProject.getId(), entityID, EntityType.GROUP);
		
		assertNotNull(permission);
		assertNotNull(permission.getRoleIds());
		
	}
	
	@Test
	public void testBasicUserShouldNotAddProjectPermission() throws Exception {
		
		AuthUser authUser = authService.projectUser(existingProject);
		
		long nbPermissions = permissionDAO.count();
		
		mvc
		.perform(
				post(buildAddFormURL(existingProject))
				.param("projectId", existingProject.getId())
				.param("entityType", EntityType.USER.toString())
				.param("entityId", existingUser.getId())
				.param("roleIds", roles.get(0).getId())
				.with(user(authUser)))
		.andExpect(status().isForbidden());
		
		assertEquals(nbPermissions, permissionDAO.count());
		
	}
	
	@Test
	public void testshouldNotCreateTwoProjectPermissionsOnSameEntity() throws Exception {
		
		AuthUser authUser = authService.projectManager(existingProject);
		
		ProjectPermission permission = new ProjectPermission();
		permission.setProjectId(existingProject.getId());
		permission.setEntityType(EntityType.USER);
		permission.setEntityId(existingUser.getId());
		permission.setRoleIds(Lists.newArrayList(roles.get(0).getId()));
		
		gate.dispatch(new CreateProjectPermissionCommand(permission));
		
		long nbPermissions = permissionDAO.count();
		
		mvc
			.perform(
					post(buildAddFormURL(existingProject))
					.param("projectId", existingProject.getId())
					.param("entityType", permission.getEntityType().toString())
					.param("entityId", permission.getEntityId())
					.param("roleIds", roles.get(0).getId())
					.with(user(authUser)))
			.andExpect(status().isOk());
		
		assertEquals(nbPermissions, permissionDAO.count());
		
	}
	
	@Test
	public void testShouldNotCreateProjectPermissionWithoutEntity() throws Exception {
		
		AuthUser authUser = authService.projectManager(existingProject);
		
		long nbPermissions = permissionDAO.count();
		
		mvc
			.perform(
					post(buildAddFormURL(existingProject))
					.param("projectId", existingProject.getId())
					.param("entityType", EntityType.USER.toString())
					.param("entityId", "")
					.param("roleIds", roles.get(0).getId())
					.with(user(authUser)))
			.andExpect(status().isOk());
		
		assertEquals(nbPermissions, permissionDAO.count());
	}
	
	@Test
	public void testShouldNotCreateProjectPermissionWithoutEntityType() throws Exception {
		
		AuthUser authUser = authService.projectManager(existingProject);
		
		long nbPermissions = permissionDAO.count();
		
		mvc
			.perform(
					post(buildAddFormURL(existingProject))
					.param("projectId", existingProject.getId())
					.param("entityType", "")
					.param("entityId", existingUser.getId())
					.param("roleIds", roles.get(0).getId())
					.with(user(authUser)))
			.andExpect(status().isOk());
		
		assertEquals(nbPermissions, permissionDAO.count());
	}
	
	@Test
	public void testShouldNotCreateProjectPermissionWithoutRoleIds() throws Exception {
		
		AuthUser authUser = authService.projectManager(existingProject);
		
		long nbPermissions = permissionDAO.count();
		
		mvc
			.perform(
					post(buildAddFormURL(existingProject))
					.param("projectId", existingProject.getId())
					.param("entityType", EntityType.USER.toString())
					.param("entityId", existingUser.getId())
					.param("roleIds", "")
					.with(user(authUser)))
			.andExpect(status().isOk());
		
		assertEquals(nbPermissions, permissionDAO.count());
	}
	
	@Test
	public void testShouldNotCreateProjectPermissionWithoutProjectId() throws Exception {
		
		AuthUser authUser = authService.projectManager(existingProject);
		
		long nbPermissions = permissionDAO.count();
		
		mvc
			.perform(
					post(buildAddFormURL(existingProject))
					.param("projectId", "")
					.param("entityType", EntityType.USER.toString())
					.param("entityId", existingUser.getId())
					.param("roleIds", roles.get(0).getId())
					.with(user(authUser)))
			.andExpect(status().isOk());
		
		assertEquals(nbPermissions, permissionDAO.count());
	}
	
}
