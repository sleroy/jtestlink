package com.tocea.corolla.portfolio.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.portfolio.commands.MovePortfolioNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.exceptions.PortfolioNotFoundException;
import com.tocea.corolla.trees.commands.MoveTreeNodeCommand;

@CommandHandler
@Transactional
public class MovePortfolioNodeCommandHandler implements ICommandHandler<MovePortfolioNodeCommand, Portfolio> {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private Gate gate;
	
	@Override
	public Portfolio handle(@Valid MovePortfolioNodeCommand command) {
		
		Portfolio portfolio = portfolioDAO.find();
		
		if (portfolio == null) {
			throw new PortfolioNotFoundException();
		}
		
		Integer nodeId = command.getNodeID();
		
		Integer newParentId = command.getNewParentID();
		
		portfolio = gate.dispatch(new MoveTreeNodeCommand(portfolio, nodeId, newParentId));
		
		portfolioDAO.save(portfolio);
		
		return portfolio;
		
	}

}
