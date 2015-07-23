package com.tocea.corolla.portfolio.rest;

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
import com.tocea.corolla.portfolio.commands.CreatePortfolioTextNodeCommand;
import com.tocea.corolla.portfolio.commands.EditPortfolioTextNodeCommand;
import com.tocea.corolla.portfolio.commands.MovePortfolioNodeCommand;
import com.tocea.corolla.portfolio.commands.RemovePortfolioNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.utils.PortfolioUtils;
import com.tocea.corolla.portfolio.visitors.PortfolioJsTree;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.trees.domain.TextNode;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.dto.JsTreeNodeDTO;
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
	private Gate gate;
	
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
	
	@RequestMapping(value = "/edit/text/{nodeID}", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public Portfolio editTextNode(@PathVariable Integer nodeID, @RequestBody String text) {
		
		return gate.dispatch(new EditPortfolioTextNodeCommand(nodeID, text));
	}
	
	@RequestMapping(value = "/add/text/{parentID}", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public TextNode addTextNode(@PathVariable Integer parentID, @RequestBody String text) {
		
		Portfolio portfolio = gate.dispatch(new CreatePortfolioTextNodeCommand(text, parentID));
		
		Integer maxID = TreeNodeUtils.getMaxNodeId(portfolio.getNodes());		
		TreeNode node = TreeNodeUtils.getNodeById(maxID, portfolio.getNodes());
		
		if (node != null && TreeNodeUtils.isTextNode(node) && ((TextNode)node).getText().equals(text)) {
			return (TextNode) node;		
		}
		
		return null;
	}
	
	@RequestMapping(value = "/add/text", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.REST, Permission.PORTFOLIO_MANAGEMENT })
	public TextNode addTextNode(@RequestBody String text) {
		
		return addTextNode(null, text);
	}
	
}