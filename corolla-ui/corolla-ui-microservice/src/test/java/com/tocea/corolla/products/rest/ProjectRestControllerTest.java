package com.tocea.corolla.products.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.servlet.Filter;

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
}