package com.tocea.corolla.ui.views.admin.folders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.tests.utils.AuthUserService;
import com.tocea.corolla.trees.commands.CreateFolderNodeTypeCommand;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.ui.AbstractSpringTest;

public class FolderNodeTypePageControllerTest extends AbstractSpringTest {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	 
	private MockMvc mvc;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private AuthUserService authService;
	
	private FolderNodeType existingType;
	
	@Before
	public void setUp() {
		
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain)
				.build();
		
		existingType = gate.dispatch(new CreateFolderNodeTypeCommand(new FolderNodeType("blblbl", "http://awesome-icon.com/image.png")));
		
	}
	
	@Test
	public void adminShouldAccessListView() throws Exception {
		
		mvc
		.perform(get("/ui/admin/folder-node-types").with(user(authService.admin())))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void basicUserShouldNotAccessListView() throws Exception {
		
		mvc
		.perform(get("/ui/admin/folder-node-types").with(user(authService.basicUser())))
		.andExpect(status().isForbidden());
		
	}
	
	@Test
	public void adminShouldAccessAddFormView() throws Exception {
		
		mvc
		.perform(get("/ui/admin/folder-node-types/add").with(user(authService.admin())))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void basicUserShouldNotAccessAddFormView() throws Exception {
		
		mvc
		.perform(get("/ui/admin/folder-node-types/add").with(user(authService.basicUser())))
		.andExpect(status().isForbidden());
		
	}
	
	@Test
	public void adminShouldAddNewFolderType() throws Exception {
		
		mvc
		.perform(
				post("/ui/admin/folder-node-types/add")
				.param("name", "blblbl")
				.param("icon", "http://awesome-icon.com/image.png")
				.with(user(authService.admin())))
		.andExpect(status().isFound());
		
	}
	
	@Test
	public void adminShouldAccessEditFormView() throws Exception {
		
		mvc
		.perform(
				get("/ui/admin/folder-node-types/edit/"+existingType.getId())
				.with(user(authService.admin()))
		)
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void basicUserShouldNotAccessEditFormView() throws Exception {
		
		mvc
		.perform(
				get("/ui/admin/folder-node-types/edit/"+existingType.getId())
				.with(user(authService.basicUser()))
		)
		.andExpect(status().isForbidden());
		
	}
	
	@Test
	public void adminShouldEditFolderType() throws Exception {
		
		mvc
		.perform(
				post("/ui/admin/folder-node-types/edit/"+existingType.getId())
				.param("id", existingType.getId())
				.param("name", "blblbl")
				.param("icon", "http://awesome-icon.com/image.png")
				.with(user(authService.admin())))
		.andExpect(status().isFound());
		
	}
	
}
