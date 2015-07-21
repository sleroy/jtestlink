package com.tocea.corolla.portfolio.commands.handlers;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.portfolio.commands.CreatePortfolioCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.trees.domain.TreeNode;

@CommandHandler
@Transactional
public class CreatePortfolioCommandHandler implements ICommandHandler<CreatePortfolioCommand, Portfolio> {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Override
	public Portfolio handle(CreatePortfolioCommand command) {
		
		Portfolio portfolio = new Portfolio();
		portfolio.setNodes(new ArrayList<TreeNode>());
		
		portfolioDAO.save(portfolio);
		
		return portfolio;
	}
	
}
