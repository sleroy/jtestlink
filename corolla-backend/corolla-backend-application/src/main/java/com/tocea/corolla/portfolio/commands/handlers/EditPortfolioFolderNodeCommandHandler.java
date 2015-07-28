package com.tocea.corolla.portfolio.commands.handlers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.portfolio.commands.EditPortfolioFolderNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.exceptions.PortfolioNotFoundException;
import com.tocea.corolla.trees.commands.EditTextNodeCommand;

@CommandHandler
public class EditPortfolioFolderNodeCommandHandler implements ICommandHandler<EditPortfolioFolderNodeCommand, Portfolio> {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private Gate gate;
	
	@Override
	public Portfolio handle(@Valid EditPortfolioFolderNodeCommand command) {
		
		Portfolio portfolio = portfolioDAO.find();
		
		if (portfolio == null) {
			throw new PortfolioNotFoundException();
		}
		
		portfolio = gate.dispatch(new EditTextNodeCommand(portfolio, command.getNodeID(), command.getText()));
		
		portfolioDAO.save(portfolio);
		
		return portfolio;
	}

	
}
