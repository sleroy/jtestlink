package com.tocea.corolla.portfolio.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.portfolio.commands.RemovePortfolioNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.portfolio.exceptions.PortfolioNotFoundException;
import com.tocea.corolla.portfolio.utils.PortfolioUtils;
import com.tocea.corolla.products.commands.DeleteProjectCommand;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
@Transactional
public class RemovePortfolioNodeCommandHandler implements ICommandHandler<RemovePortfolioNodeCommand, Portfolio> {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public Portfolio handle(@Valid RemovePortfolioNodeCommand command) {
		
		Portfolio portfolio = portfolioDAO.find();
		
		if (portfolio == null) {
			throw new PortfolioNotFoundException();
		}
		
		Integer nodeID = command.getNodeID();	
		
		TreeNode node = TreeNodeUtils.getNodeById(nodeID, portfolio.getNodes());
		
		if (node != null) {
			removeNode(node);
		}
		
		treeManagementService.removeNode(portfolio, nodeID);
		
		portfolioDAO.save(portfolio);
		
		return portfolio;
	}
	
	private void removeNode(TreeNode node) {
		
		if (PortfolioUtils.isProjectNode(node)) {
			gate.dispatch(new DeleteProjectCommand(((ProjectNode) node).getProjectId()));
		}
		
		for(TreeNode child : node.getNodes()) {
			removeNode(child);
		}
		
	}

}
