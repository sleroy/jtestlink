package com.tocea.corolla.products.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.utils.PortfolioUtils;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.dto.JsTreeNodeDTO;
import com.tocea.corolla.trees.dto.JsTreeNodeVisitor;
import com.tocea.corolla.users.domain.Permission;

@RestController
@RequestMapping("/rest/portfolio")
public class PortfolioRestController {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@RequestMapping(value = "/jstree")
	@Secured({ Permission.REST })
	public Collection<JsTreeNodeDTO> getJsTree() {
		
		Portfolio portfolio = portfolioDAO.find();
		
		Collection<String> ids = PortfolioUtils.getProjectIDs(portfolio.getNodes());
		
		Collection<Project> projects = Lists.newArrayList(projectDAO.findAll(ids));
		
		Collection<JsTreeNodeDTO> nodes = Lists.newArrayList();
		
		for(TreeNode node : portfolio.getNodes()) {
			
			JsTreeNodeVisitor visitor = new JsTreeNodeVisitor(projects);
			node.accept(visitor);
			nodes.add(visitor.getResult());
			
		}

		return nodes;		
	}
	
}
