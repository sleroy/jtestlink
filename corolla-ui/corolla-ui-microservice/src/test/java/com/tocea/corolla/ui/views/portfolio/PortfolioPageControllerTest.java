package com.tocea.corolla.ui.views.portfolio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.products.commands.CreateProjectCommand;
import com.tocea.corolla.products.commands.CreateProjectStatusCommand;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.revisions.services.IRevisionService;
import com.tocea.corolla.tests.utils.AuthUserService;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.ui.AbstractSpringTest;

public class PortfolioPageControllerTest extends AbstractSpringTest {

	private static final String PORTFOLIO_URL 			= "/ui/portfolio";
	private static final String PORTFOLIO_MANAGER_URL 	= PORTFOLIO_URL+"/manager/";
	private static final String CREATE_PROJECT_URL		= PORTFOLIO_MANAGER_URL+"create";
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	 
	private MockMvc mvc;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Autowired
	private AuthUserService authService;
	
	private ProjectStatus projectStatus;
	private Project existingProject;
	
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
		
	}
	
	@After
	public void tearDown() {
		projectDAO.deleteAll();
	}
	
	@Test
	public void basicUserShouldAccessPortfolioView() throws Exception {
		
		mvc
		.perform(get(PORTFOLIO_URL).with(user(authService.portfolioUser())))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void anonymousUserShouldNotAccessPortfolioView() throws Exception {
		
		mvc.perform(get(PORTFOLIO_URL))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void basicUserShouldAccessPortfolioManagerView() throws Exception {
		
		mvc
			.perform(
				get(PORTFOLIO_MANAGER_URL)
				.with(user(authService.portfolioUser())))
			.andExpect(status().isOk());
		
	}
	
	@Test
	public void anonymousUserShouldNotAccessPortfolioManagerView() throws Exception {
		
		mvc.perform(get(PORTFOLIO_MANAGER_URL))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void basicUserShouldAccessProjectManagementView() throws Exception {
		
		mvc
		.perform(
				get(PORTFOLIO_MANAGER_URL+existingProject.getKey())
				.with(user(authService.projectUser(existingProject))))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void shouldRedirectIfProjectDoesNotExist() throws Exception {
		 
		mvc
			.perform(
					get(PORTFOLIO_MANAGER_URL+"blblbl")
					.with(user(authService.portfolioUser())))
			.andExpect(status().isForbidden());
		
	}
	
	@Test
	public void managerUserShouldCreateProject() throws Exception {
		
		String key = "the_key";
		String name = "a name";
		Integer parentID = 1;
		
		mvc
			.perform(
				post(CREATE_PROJECT_URL)
				.param("key", key)
				.param("name", name)
				.param("parentID", parentID.toString())
				.with(user(authService.projectCreator())))
			.andExpect(status().isFound());

		Project project =projectDAO.findByKey(key);
		
		assertNotNull(project);
		assertEquals(name, project.getName());
		
		TreeNode node = treeManagementService.findNode(portfolioDAO.find(), new FindNodeByIDPredicate(parentID));
		
		assertEquals(1, node.getNodes().size());
		assertEquals(project.getId(), ((ProjectNode) node.getNodes().iterator().next()).getProjectId());
		
	}
	
	@Test
	public void basicUserShouldNotCreateProject() throws Exception {
		
		long nbProjects = projectDAO.count();
		
		String key = "the_key";
		String name = "a name";
		Integer parentID = 1;
		
		mvc
			.perform(
				post(CREATE_PROJECT_URL)
				.param("key", key)
				.param("name", name)
				.param("parentID", parentID.toString())
				.with(user(authService.portfolioUser())))
			.andExpect(status().isForbidden());

		assertEquals(nbProjects, projectDAO.count());
		
	}
	
}
