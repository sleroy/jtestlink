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
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.ui.AbstractSpringTest;
import com.tocea.corolla.ui.security.AuthUser;
import com.tocea.corolla.users.domain.Role;
import com.tocea.corolla.users.domain.User;

public class PortfolioPageControllerTest extends AbstractSpringTest {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	 
	private MockMvc mvc;
	
	@Autowired
	private Gate gate;
	
	private Role basicRole;
	
	private User basicUser;
	
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
		
		existingProject = new Project();
		existingProject.setKey("COROLLA_TEST");
		existingProject.setName("Corolla");
		
		gate.dispatch(new CreateProjectCommand(existingProject));
		
	}
	
	@Test
	public void basicUserShouldAccessPortfolioView() throws Exception {
		
		mvc
		.perform(get("/ui/portfolio").with(user(new AuthUser(basicUser, basicRole))))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void anonymousUserShouldNotAccessPortfolioView() throws Exception {
		
		mvc.perform(get("/ui/portfolio"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void basicUserShouldAccessPortfolioManagerView() throws Exception {
		
		mvc
		.perform(get("/ui/portfolio/manager").with(user(new AuthUser(basicUser, basicRole))))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void anonymousUserShouldNotAccessPortfolioManagerView() throws Exception {
		
		mvc.perform(get("/ui/portfolio/manager"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void basicUserShouldAccessProjectManagementView() throws Exception {
		
		mvc
		.perform(
				get("/ui/portfolio/manager/"+existingProject.getKey())
				.with(user(new AuthUser(basicUser, basicRole))))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void shouldRedirectIfProjectDoesNotExist() throws Exception {
		 
		mvc
			.perform(
					get("/ui/portfolio/manager/"+"blblbl")
					.with(user(new AuthUser(basicUser, basicRole))))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/ui/portfolio/manager"));
		
	}
	
}