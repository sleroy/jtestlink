package com.tocea.corolla.ui;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tocea.corolla.ui.security.AuthService;

public class AuthenticationTest extends AbstractSpringTest {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	
	@Autowired
	private AuthService authService;
	 
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain)
				.build();
		
	}
	
	@Test
	public void testRequireAuthentication() throws Exception {
		
		mvc.perform(get("/ui/home")).andExpect(status().isFound());
		
	}
	
	@Test
	public void testAuthenticationSuccess() throws Exception {
		
		mvc
			.perform(formLogin().user("jsnow"))
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/home"))
			.andExpect(authenticated().withUsername("jsnow"));
		
	}

}
