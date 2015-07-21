package com.tocea.corolla.portfolio.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.portfolio.commands.CreatePortfolioCommand;
import com.tocea.corolla.portfolio.commands.CreatePortfolioTextNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand;
import com.tocea.corolla.trees.domain.TextNode;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;

@CommandHandler
@Transactional
public class CreatePortfolioTextNodeCommandHandler implements ICommandHandler<CreatePortfolioTextNodeCommand, Portfolio> {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private Gate gate;
	
	@Override
	public Portfolio handle(@Valid CreatePortfolioTextNodeCommand command) {
		
		Portfolio portfolio = portfolioDAO.find();
			
		if (portfolio == null) {
			gate.dispatch(new CreatePortfolioCommand());
		}
		
		String text = command.getText();
		
		if (StringUtils.isEmpty(text)) {
			throw new InvalidTreeNodeInformationException("The text of a text node cannot be empty");
		}
		
		Integer parentID = command.getParentID();
		
		TreeNode node = new TextNode(text);
		
		portfolio = (Portfolio) gate.dispatch(new CreateTreeNodeCommand(portfolio, node, parentID));
		
		portfolioDAO.save(portfolio);
		
		return portfolio;
	}

}
