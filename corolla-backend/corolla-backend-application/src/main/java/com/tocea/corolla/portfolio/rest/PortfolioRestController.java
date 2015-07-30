package com.tocea.corolla.portfolio.rest;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.portfolio.commands.ChangePortfolioFolderNodeTypeCommand;
import com.tocea.corolla.portfolio.commands.CreatePortfolioFolderNodeCommand;
import com.tocea.corolla.portfolio.commands.EditPortfolioFolderNodeCommand;
import com.tocea.corolla.portfolio.commands.MovePortfolioNodeCommand;
import com.tocea.corolla.portfolio.commands.RemovePortfolioNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.utils.PortfolioUtils;
import com.tocea.corolla.portfolio.visitors.PortfolioJsTree;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.dto.JsTreeNodeDTO;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.trees.utils.TreeNodeUtils;
import com.tocea.corolla.users.domain.Permission;

@RestController
@RequestMapping("/rest/portfolio")
public class PortfolioRestController {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@RequestMapping(value = "/")
	@Secured({ Permission.REST })
	public Collection<TreeNode> getTree() {
		
		Portfolio portfolio = portfolioDAO.find();
		
		return portfolio != null ? portfolio.getNodes() : new ArrayList<TreeNode>();
	}
	
	@RequestMapping(value = "/jstree")
	@Secured({ Permission.REST })
	public Collection<JsTreeNodeDTO> getJsTree() {
		
		Portfolio portfolio = portfolioDAO.find();
		
		Collection<String> ids = PortfolioUtils.getProjectIDs(portfolio.getNodes());
		
		Collection<Project> projects = Lists.newArrayList(projectDAO.findAll(ids));
		
		Collection<JsTreeNodeDTO> nodes = Lists.newArrayList();
		
		for(TreeNode node : portfolio.getNodes()) {
			
			PortfolioJsTree visitor = new PortfolioJsTree(projects);
			node.accept(visitor);
			nodes.add(visitor.getResult());
			
		}

		return nodes;		
	}
	
	@RequestMapping(value = "/move/{fromID}/{toID}")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public Portfolio moveNode(@PathVariable Integer fromID, @PathVariable Integer toID) {
		
		return gate.dispatch(new MovePortfolioNodeCommand(fromID, toID != 0 ? toID : null));
	}
	
	@RequestMapping(value = "/remove/{nodeID}")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public Portfolio removeNode(@PathVariable Integer nodeID) {
		
		return gate.dispatch(new RemovePortfolioNodeCommand(nodeID));
	}
	
	@RequestMapping(value = "/folders/edit/{nodeID}", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public Portfolio editTextNode(@PathVariable Integer nodeID, @RequestBody String text) {
		
		return gate.dispatch(new EditPortfolioFolderNodeCommand(nodeID, text));
	}
	
	@RequestMapping(value = "/folders/add/{parentID}/{folderNodeTypeID}", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public FolderNode addTextNode(@PathVariable Integer parentID, @PathVariable String folderNodeTypeID, @RequestBody String text) {
		
		FolderNodeType folderNodeType = folderNodeTypeDAO.findOne(folderNodeTypeID);
		
		return gate.dispatch(new CreatePortfolioFolderNodeCommand(text, folderNodeType, parentID));
	}
	
	@RequestMapping(value = "/folders/add/{folderNodeTypeID}", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public FolderNode addTextNode(@PathVariable String folderNodeTypeID, @RequestBody String text) {
		
		return addTextNode(null, folderNodeTypeID, text);
	}
	
	@RequestMapping(value = "/folders/edit/type/{nodeID}/{typeID}")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public FolderNode changeFolderNodeType(@PathVariable Integer nodeID, @PathVariable String typeID) {
		
		return gate.dispatch(new ChangePortfolioFolderNodeTypeCommand(nodeID, typeID));
	}
	
}