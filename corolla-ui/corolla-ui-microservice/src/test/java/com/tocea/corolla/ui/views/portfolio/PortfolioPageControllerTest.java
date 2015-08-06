package com.tocea.corolla.ui.views.portfolio;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectStatus;
import com.tocea.corolla.revisions.services.IRevisionService;
import com.tocea.corolla.ui.AbstractSpringTest;
import com.tocea.corolla.ui.security.AuthUser;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;

public class PortfolioPageControllerTest extends AbstractSpringTest {

	private static final String PORTFOLIO_URL 			= "/ui/portfolio";
	private static final String PORTFOLIO_MANAGER_URL 	= PORTFOLIO_URL+"/manager/";
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	 
	private MockMvc mvc;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private IRevisionService revisionService;
	
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
	
	@Test
	public void basicUserShouldAccessPortfolioView() throws Exception {
		
		mvc
		.perform(get(PORTFOLIO_URL).with(user(new AuthUser(basicUser, basicRole))))
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
		.perform(get(PORTFOLIO_MANAGER_URL).with(user(new AuthUser(basicUser, basicRole))))
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
				.with(user(new AuthUser(basicUser, basicRole))))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void shouldRedirectIfProjectDoesNotExist() throws Exception {
		 
		mvc
			.perform(
					get(PORTFOLIO_MANAGER_URL+"blblbl")
					.with(user(new AuthUser(basicUser, basicRole))))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl(PORTFOLIO_MANAGER_URL));
		
	}
	
}