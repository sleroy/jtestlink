package com.tocea.corolla.portfolio.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.portfolio.commands.CreatePortfolioFolderNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.portfolio.predicates.FindNodeByProjectIDPredicate;
import com.tocea.corolla.products.commands.CreateProjectCommand;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.tests.utils.AuthUserService;
import com.tocea.corolla.trees.commands.CreateFolderNodeTypeCommand;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.ui.AbstractSpringTest;

public class PortfolioRestControllerTest extends AbstractSpringTest {

	private static final String PORTFOLIO_URL 				= "/rest/portfolio/";
	private static final String PORTFOLIO_JSTREE_URL 		= PORTFOLIO_URL+"jstree";
	private static final String PORTFOLIO_MOVE_URL 			= PORTFOLIO_URL+"move/";
	private static final String PORTFOLIO_REMOVE_URL 		= PORTFOLIO_URL+"remove/";
	private static final String PORTFOLIO_FOLDER_EDIT_URL 	= PORTFOLIO_URL+"folders/edit/";
	private static final String PORTFOLIO_FOLDER_ADD_URL 	= PORTFOLIO_URL+"folders/add/";
	private static final String PORTFOLIO_CHANGE_TYPE_URL 	= PORTFOLIO_URL+"/folders/edit/type/";
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Filter springSecurityFilterChain;
	 
	private MockMvc mvc;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private AuthUserService authUserService;
	
	private Project existingProject;	
	private FolderNodeType folderNodeType;
	private FolderNodeType otherFolderNodeType;
	private ProjectNode corollaNode;
	private FolderNode corollaFolder;
	
	@Before
	public void setUp() {
		
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.addFilters(springSecurityFilterChain)
				.build();
		
		existingProject = new Project();
		existingProject.setKey("COROLLA_TEST");
		existingProject.setName("Corolla");
		
		gate.dispatch(new CreateProjectCommand(existingProject));
		
		Portfolio portfolio = portfolioDAO.find();
		corollaNode = (ProjectNode) treeManagementService.findNode(portfolio, new FindNodeByProjectIDPredicate(existingProject.getId()));
		
		folderNodeType = new FolderNodeType("Tests", "http://awesome-icons.com/image.png");
		gate.dispatch(new CreateFolderNodeTypeCommand(folderNodeType));
		
		otherFolderNodeType = new FolderNodeType("Others", "http://awesome-icons.com/image.png");
		gate.dispatch(new CreateFolderNodeTypeCommand(otherFolderNodeType));
		
		corollaFolder = gate.dispatch(new CreatePortfolioFolderNodeCommand("corolla", folderNodeType, null));
		
	}
	
	@Test
	public void basicUserShouldAccessPortfolioTree() throws Exception {
		
		mvc
		.perform(get(PORTFOLIO_URL).with(user(authUserService.portfolioUser())))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void anonymousUserShouldNotAccessPortfolioTree() throws Exception {
		
		mvc.perform(get(PORTFOLIO_URL))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void basicUserShouldAccessPortfolioJsTree() throws Exception {
		
		mvc
		.perform(get(PORTFOLIO_JSTREE_URL).with(user(authUserService.portfolioUser())))
		.andExpect(status().isOk());
		
	}
	
	@Test
	public void anonymousUserShouldNotAccessPortfolioJsTree() throws Exception {
		
		mvc.perform(get(PORTFOLIO_JSTREE_URL))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"));
		
	}
	
	@Test
	public void basicUserShouldAccessPortfolioJsTreeSubTree() throws Exception {
		
		mvc
		.perform(
				get(PORTFOLIO_JSTREE_URL+"/"+existingProject.getKey())
				.with(user(authUserService.projectUser(existingProject)))
		)
		.andExpect(status().isOk());
	}
	
	@Test
	public void anonymousUserShouldNotAccessPortfolioJsTreeSubTree() throws Exception {
		
		mvc.perform(get(PORTFOLIO_JSTREE_URL+"/"+existingProject.getKey()))
			.andExpect(status().isFound())
			.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	public void managerUserShouldMovePortfolioNode() throws Exception {
		
		assertEquals(2, portfolioDAO.find().getNodes().size());
		
		StringBuilder url = 
				new StringBuilder(PORTFOLIO_MOVE_URL)
				.append(corollaNode.getId())
				.append("/")
				.append(corollaFolder.getId());
		
		mvc
		.perform(get(url.toString()).with(user(authUserService.portfolioManager())))
		.andExpect(status().isOk())
		.andReturn();
		
		Portfolio portfolio = portfolioDAO.find();
		assertEquals(1, portfolio.getNodes().size());
		assertEquals(1, portfolio.getNodes().iterator().next().getNodes().size());
		
	}
	
	@Test
	public void basicUserShouldNotMovePortfolioNode() throws Exception {
		
		StringBuilder url = 
				new StringBuilder(PORTFOLIO_MOVE_URL)
				.append(corollaNode.getId())
				.append("/")
				.append(corollaFolder.getId());
		
		mvc
		.perform(get(url.toString()).with(user(authUserService.portfolioUser())))
		.andExpect(status().isForbidden());
		
	}
	
	@Test
	public void managerUserShouldRemovePortfolioNode() throws Exception {
		
		assertEquals(2, portfolioDAO.find().getNodes().size());
		
		StringBuilder url = 
				new StringBuilder(PORTFOLIO_REMOVE_URL)
				.append(corollaFolder.getId());
		
		mvc
			.perform(get(url.toString()).with(user(authUserService.portfolioManager())))
			.andExpect(status().isOk())
			.andReturn();
		
		assertEquals(1, portfolioDAO.find().getNodes().size());
		
	}
	
	@Test
	public void basicUserShouldNotRemovePortfolioNode() throws Exception {
		
		StringBuilder url = 
				new StringBuilder(PORTFOLIO_REMOVE_URL)
				.append(corollaFolder.getId());
		
		mvc
		.perform(get(url.toString()).with(user(authUserService.portfolioUser())))
		.andExpect(status().isForbidden());
		
	}
	
	@Test
	public void managerUserShouldEditPortfolioFolderNode() throws Exception {
		
		String newText = "NEW_TEXT";
		
		StringBuilder url = new StringBuilder(PORTFOLIO_FOLDER_EDIT_URL).append(corollaFolder.getId());
		
		mvc
			.perform(
					post(url.toString())
					.content(newText)
					.contentType(MediaType.TEXT_PLAIN)
					.with(user(authUserService.portfolioManager())))
			.andExpect(status().isOk())
			.andReturn();
		
		corollaFolder = (FolderNode) treeManagementService.findNode(portfolioDAO.find(), new FindNodeByIDPredicate(corollaFolder.getId()));
		assertEquals(newText, corollaFolder.getText());
		
	}
	
	@Test
	public void basicUserShouldNotEditPortfolioFolderNode() throws Exception {
		
		String newText = "NEW_TEXT";
		
		StringBuilder url = new StringBuilder(PORTFOLIO_FOLDER_EDIT_URL).append(corollaFolder.getId());
		
		mvc
			.perform(
					post(url.toString())
					.content(newText)
					.contentType(MediaType.TEXT_PLAIN)
					.with(user(authUserService.portfolioUser())))
			.andExpect(status().isForbidden())
			.andReturn();
		
		corollaFolder = (FolderNode) treeManagementService.findNode(portfolioDAO.find(), new FindNodeByIDPredicate(corollaFolder.getId()));
		assert !corollaFolder.getText().equals(newText);
		
	}
	
	@Test
	public void managerUserShouldAddPortfolioFolderNode() throws Exception {
		
		String newText = "NEW_TEXT";
		
		StringBuilder url = 
				new StringBuilder(PORTFOLIO_FOLDER_ADD_URL)
				.append(corollaFolder.getId())
				.append("/")
				.append(folderNodeType.getId());
		
		mvc
			.perform(
					post(url.toString())
					.content(newText)
					.contentType(MediaType.TEXT_PLAIN)
					.with(user(authUserService.portfolioManager())))
			.andExpect(status().isOk())
			.andReturn();
		
		corollaFolder = (FolderNode) treeManagementService.findNode(portfolioDAO.find(), new FindNodeByIDPredicate(corollaFolder.getId()));
		assertEquals(1, corollaFolder.getNodes().size());
		assertEquals(newText, ((FolderNode) corollaFolder.getNodes().iterator().next()).getText());
		
	}
	
	@Test
	public void basicUserShouldNotAddPortfolioFolderNode() throws Exception {
		
		String newText = "NEW_TEXT";
		
		StringBuilder url = 
				new StringBuilder(PORTFOLIO_FOLDER_ADD_URL)
				.append(corollaFolder.getId())
				.append("/")
				.append(folderNodeType.getId());
		
		mvc
			.perform(
					post(url.toString())
					.content(newText)
					.contentType(MediaType.TEXT_PLAIN)
					.with(user(authUserService.portfolioUser())))
			.andExpect(status().isForbidden())
			.andReturn();
		
		corollaFolder = (FolderNode) treeManagementService.findNode(portfolioDAO.find(), new FindNodeByIDPredicate(corollaFolder.getId()));
		assertEquals(0, corollaFolder.getNodes().size());
		
	}
	
	@Test
	public void managerUserShouldAddPortfolioFolderNodeAtTreeRoot() throws Exception {
		
		String newText = "NEW_TEXT";
		
		StringBuilder url = new StringBuilder(PORTFOLIO_FOLDER_ADD_URL).append(folderNodeType.getId());
		
		mvc
			.perform(
					post(url.toString())
					.content(newText)
					.contentType(MediaType.TEXT_PLAIN)
					.with(user(authUserService.portfolioManager())))
			.andExpect(status().isOk())
			.andReturn();
		
		assertEquals(3, portfolioDAO.find().getNodes().size());
		
	}
	
	@Test
	public void basicUserShouldNotAddPortfolioFolderNodeAtTreeRoot() throws Exception {
		
		String newText = "NEW_TEXT";
		
		StringBuilder url = new StringBuilder(PORTFOLIO_FOLDER_ADD_URL).append(folderNodeType.getId());
		
		mvc
			.perform(
					post(url.toString())
					.content(newText)
					.contentType(MediaType.TEXT_PLAIN)
					.with(user(authUserService.portfolioUser())))
			.andExpect(status().isForbidden())
			.andReturn();
		
		assertEquals(2, portfolioDAO.find().getNodes().size());
		
	}
	
	@Test
	public void managerUserShouldChangePortfolioFolderNodeType() throws Exception {
		
		StringBuilder url = 
				new StringBuilder(PORTFOLIO_CHANGE_TYPE_URL)
				.append(corollaFolder.getId())
				.append("/")
				.append(otherFolderNodeType.getId());
		
		mvc
			.perform(
					get(url.toString())
					.with(user(authUserService.portfolioManager())))
			.andExpect(status().isOk())
			.andReturn();
		
		corollaFolder = (FolderNode) treeManagementService.findNode(portfolioDAO.find(), new FindNodeByIDPredicate(corollaFolder.getId()));
		
		assertEquals(otherFolderNodeType.getId(), corollaFolder.getTypeID());
		
	}
	
	@Test
	public void basicUserShouldNotChangePortfolioFolderNodeType() throws Exception {
		
		StringBuilder url = 
				new StringBuilder(PORTFOLIO_CHANGE_TYPE_URL)
				.append(corollaFolder.getId())
				.append("/")
				.append(otherFolderNodeType.getId());
		
		mvc
			.perform(
					get(url.toString())
					.with(user(authUserService.portfolioUser())))
			.andExpect(status().isForbidden())
			.andReturn();
		
		corollaFolder = (FolderNode) treeManagementService.findNode(portfolioDAO.find(), new FindNodeByIDPredicate(corollaFolder.getId()));
		
		assert !otherFolderNodeType.getId().equals(corollaFolder.getTypeID());
		
	}
	
}
