package com.tocea.corolla.ui.views.projects;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.CreateProjectCommand;
import com.tocea.corolla.products.commands.CreateProjectStatusCommand;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.revisions.domain.ICommit;
import com.tocea.corolla.revisions.services.IRevisionService;
import com.tocea.corolla.ui.AbstractSpringTest;
import com.tocea.corolla.ui.security.AuthUser;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;

public class ProjectDetailsPageControllerTest extends AbstractSpringTest {

	private static final String PROJECTS_URL 			= "/ui/projects/";
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	 
	private MockMvc mvc;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Autowired
	private IProjectDAO projectDAO;
	
	private Role basicRole;	
	private User basicUser;
	
	private ProjectStatus projectStatus;
	private Project existingProject;
	
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
		
		projectStatus = new ProjectStatus();
		projectStatus.setName("Active");
		projectStatus.setClosed(false);
		
		gate.dispatch(new CreateProjectStatusCommand(projectStatus));
		
		existingProject = new Project();
		existingProject.setKey("COROLLA_TEST");
		existingProject.setName("Corolla");
		existingProject.setStatusId(projectStatus.getId());
		
		gate.dispatch(new CreateProjectCommand(existingProject));
		
	}
	
	private String buildProjectURL(Project project) {
		
		return 
				new StringBuilder(PROJECTS_URL)
				.append(project.getKey())
				.toString();
		
	}
	
	private String buildProjectEditURL(Project project) {
		
		return
				new StringBuilder(PROJECTS_URL)
				.append(project.getId())
				.append("/edit")
				.toString();
	}
	
	private String buildRevisionPageURL(Project project, String commitID) {
		
		return 
				new StringBuilder(PROJECTS_URL)
				.append(project.getKey())
				.append("/revisions/")
				.append(commitID)
				.toString();
	}
	
	
	
	@Test
	public void basicUserShouldAccessProjectDetailsView() throws Exception {
		
		mvc
		.perform(get(buildProjectURL(existingProject)).with(user(new AuthUser(basicUser, basicRole))))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void anonymousUserShouldNotAccessProjectDetailsView() throws Exception {
		
		mvc.perform(get(buildProjectURL(existingProject)))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void shouldGetPageNotFoundErrorIfInvalidProjectKey() throws Exception {
		
		Project invalidProject = new Project();
		invalidProject.setKey("not_existing_key");
		
		mvc
		.perform(get(buildProjectURL(invalidProject)).with(user(new AuthUser(basicUser, basicRole))))
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void basicUserShouldEditProjectData() throws Exception {
		
		String newKey = "key_blblbl";
		String newName = "name blblbl";
		
		mvc
		.perform(
				post(buildProjectEditURL(existingProject))
				.param("id", existingProject.getId())
				.param("key", newKey)
				.param("name", newName)
				.with(user(new AuthUser(basicUser, basicRole))))
		.andExpect(status().isFound());
		
		existingProject = projectDAO.findOne(existingProject.getId());
		
		assertEquals(newKey, existingProject.getKey());
		assertEquals(newName, existingProject.getName());
		
	}
	
	@Test
	public void anonymousUserShouldNotEditProjectData() throws Exception {
		
		String newKey = "key_blblbl";
		String newName = "name blblbl";
		
		mvc
		.perform(
				post(buildProjectEditURL(existingProject))
				.param("id", existingProject.getId())
				.param("key", newKey)
				.param("name", newName)
		).andExpect(redirectedUrlPattern("**/login"));
		
		Project project = projectDAO.findOne(existingProject.getId());
		
		assertEquals(existingProject.getKey(), project.getKey());
		assertEquals(existingProject.getName(), project.getName());
		
	}
	
	@Test
	public void shouldRedirectToEditFormIfDataIsInvalid() throws Exception {
		
		String newKey = "";
		String newName = "name blblbl";
		
		mvc
		.perform(
				post(buildProjectEditURL(existingProject))
				.param("id", existingProject.getId())
				.param("key", newKey)
				.param("name", newName)
				.param("statusId", projectStatus.getId())
				.with(user(new AuthUser(basicUser, basicRole))))
		.andExpect(status().isOk());
		
		Project project = projectDAO.findOne(existingProject.getId());
		
		assertEquals(existingProject.getKey(), project.getKey());
		assertEquals(existingProject.getName(), project.getName());
		
	}
	
	@Test
	public void basicUserShouldAccessRequirementRevisionPage() throws Exception {
		
		Collection<ICommit> commits = revisionService.getHistory(existingProject.getId(), existingProject.getClass());
		
		String commitID = commits.iterator().next().getId();
		
		String URL = buildRevisionPageURL(existingProject, commitID);
		
		mvc
			.perform(
					get(URL).with(user(new AuthUser(basicUser, basicRole)))
			)
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void shouldNotAccessRequirementRevisionPageWithInvalidProjectKey() throws Exception {
		
		Collection<ICommit> commits = revisionService.getHistory(existingProject.getId(), existingProject.getClass());
		
		String commitID = commits.iterator().next().getId();
		
		Project invalidProject = new Project();
		invalidProject.setKey("blblbl");
		
		String URL = buildRevisionPageURL(invalidProject, commitID);
		
		mvc
			.perform(
					get(URL).with(user(new AuthUser(basicUser, basicRole)))
			)
			.andExpect(status().isNotFound());
		
	}
	
	@Test
	public void shouldNotAccessRequirementRevisionPageWithInvalidCommitID() throws Exception {				
		
		String URL = buildRevisionPageURL(existingProject, "blblbl");
		
		mvc
			.perform(
					get(URL).with(user(new AuthUser(basicUser, basicRole)))
			)
			.andExpect(status().isNotFound());
		
	}
}