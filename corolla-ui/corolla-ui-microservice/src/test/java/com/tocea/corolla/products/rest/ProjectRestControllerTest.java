package com.tocea.corolla.products.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.Filter;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.CreateProjectBranchCommand;
import com.tocea.corolla.products.commands.CreateProjectCommand;
import com.tocea.corolla.products.commands.CreateProjectStatusCommand;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.tests.utils.AuthUserUtils;
import com.tocea.corolla.ui.AbstractSpringTest;

public class ProjectRestControllerTest extends AbstractSpringTest {

	private static final String PROJECTS_URL 		= "/rest/projects/";
	private static final String PROJECTS_ALL_URL	= PROJECTS_URL+"all";
	private static final String TAGS_URL			= PROJECTS_URL+"/tags";
	
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
		
		projectStatus = new ProjectStatus();
		projectStatus.setName("Active");
		projectStatus.setClosed(false);
		
		gate.dispatch(new CreateProjectStatusCommand(projectStatus));
		
		existingProject = new Project();
		existingProject.setKey("COROLLA_TEST");
		existingProject.setName("Corolla");
		existingProject.setStatusId(projectStatus.getId());
		existingProject.setTags(Lists.newArrayList("tag1", "tag2"));
		
		gate.dispatch(new CreateProjectCommand(existingProject));
		
		masterBranch = branchDAO.findDefaultBranch(existingProject.getId());	
		devBranch = gate.dispatch(new CreateProjectBranchCommand("Dev", existingProject));
		
	}
	
	private String buildProjectURL(Project project) {
		
		return 
				new StringBuilder(PROJECTS_URL)
				.append(project.getKey())
				.toString();
	}
	
	private String buildDeleteBranchURL(Project project, ProjectBranch branch) {
		
		return
				new StringBuilder(buildProjectURL(project))
				.append("/branches/")
				.append(branch.getName())
				.append("/delete")
				.toString();
	}
	
	private String buildProjectTagsURL(Project project) {
		
		return
				new StringBuilder(buildProjectURL(project))
				.append("/tags")
				.toString();
	}
	
	private String buildProjectTagsPushURL(Project project) {
		
		return
				new StringBuilder(buildProjectTagsURL(project))
				.append("/push")
				.toString();
	}
	
	@Test
	public void basicUserShouldListAllProjects() throws Exception {
			
		mvc
			.perform(
					get(PROJECTS_ALL_URL)
					.with(user(AuthUserUtils.basicUser()))
			)
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void anonymousUserShouldNotListAllProjects() throws Exception {
		
		mvc
			.perform(
				get(PROJECTS_ALL_URL)
			)
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void basicUserShouldListAllProjectsFromCollectionOfIDs() throws Exception {
		
		List<String> ids = Lists.newArrayList(existingProject.getId());
		Gson gson = new Gson();
		
		mvc
			.perform(
				post(PROJECTS_ALL_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(gson.toJson(ids))
				.with(user(AuthUserUtils.basicUser()))
		)
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void projectManagerUserShouldDeleteBranch() throws Exception {
		
		mvc
			.perform(
					get(buildDeleteBranchURL(existingProject, devBranch)).
				with(user(AuthUserUtils.projectManager()))
			)
			.andExpect(status().isOk());
			
		assertNull(branchDAO.findOne(devBranch.getId()));
		
	}
	
	@Test
	public void basicUserShouldNotDeleteBranch() throws Exception {
		
		mvc
			.perform(
					get(buildDeleteBranchURL(existingProject, devBranch)).
				with(user(AuthUserUtils.basicUser()))
			)
			.andExpect(status().isForbidden());
			
		assertNotNull(branchDAO.findOne(devBranch.getId()));
		
	}
	
	@Test
	public void shouldNotDeleteDefaultBranch() throws Exception {
		
		mvc
			.perform(
				get(buildDeleteBranchURL(existingProject, masterBranch))
				.with(user(AuthUserUtils.projectManager()))
			)
			.andExpect(status().isNotAcceptable());
		
		assertNotNull(branchDAO.findOne(masterBranch.getId()));
		
	}
	
	@Test
	public void shouldRetrieveAllTags() throws Exception {
		
		Project project2 = new Project();
		project2.setKey("P2");
		project2.setName("Project 2");
		project2.setTags(Lists.newArrayList("tag2", "tag3"));
		
		gate.dispatch(new CreateProjectCommand(project2));
		
		mvc
			.perform(
					get(TAGS_URL)
					.with(user(AuthUserUtils.basicUser()))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)));
		
	}
	
	@Test
	public void anonymousUserShouldNotRetrieveAllTags() throws Exception {
		
		mvc
			.perform(
					get(TAGS_URL)
			)
			.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	public void shouldRetrieveProjectTags() throws Exception {
		
		mvc
			.perform(
					get(buildProjectTagsURL(existingProject))
					.with(user(AuthUserUtils.basicUser()))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(existingProject.getTags().size())));
		
	}
	
	@Test
	public void shouldReturnAnEmptyListOfTagsOnInvalidProjectKey() throws Exception {
		
		Project invalidProject = new Project();
		invalidProject.setKey("invalid_key");
		
		mvc
			.perform(
					get(buildProjectTagsURL(invalidProject))
					.with(user(AuthUserUtils.basicUser()))
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));
		
	}
	
	@Test
	public void anonymousUserShouldNotRetrieveProjectTags() throws Exception {
		
		mvc
			.perform(
				get(buildProjectTagsURL(existingProject))
			)
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void managerUserShouldPushProjectTags() throws Exception {
		
		List<String> newTags = Lists.newArrayList("newTags_1", "newTags_2", "newTags_3");
		
		mvc
			.perform(
				post(buildProjectTagsPushURL(existingProject))
				.content("tags="+StringUtils.join(newTags, ","))
				.with(user(AuthUserUtils.projectManager()))
			)
			.andExpect(status().isOk());

		existingProject = projectDAO.findOne(existingProject.getId());
		
		assertEquals(newTags.size(), existingProject.getTags().size());
		
		for(String newTag : newTags) {
			assert existingProject.getTags().contains(newTag);
		}
		
	}
	
	@Test
	public void basicUserShouldNotPushProjectTags() throws Exception {
		
		List<String> newTags = Lists.newArrayList("newTags_1", "newTags_2", "newTags_3");
		List<String> originTags = Lists.newArrayList(existingProject.getTags());
		
		mvc
			.perform(
				post(buildProjectTagsPushURL(existingProject))
				.content("tags="+StringUtils.join(newTags, ","))
				.with(user(AuthUserUtils.basicUser()))
			)
			.andExpect(status().isForbidden());

		existingProject = projectDAO.findOne(existingProject.getId());
		
		assertEquals(originTags, existingProject.getTags());
		
	}
	
}