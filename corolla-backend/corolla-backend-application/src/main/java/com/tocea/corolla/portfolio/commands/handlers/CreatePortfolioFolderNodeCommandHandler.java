package com.tocea.corolla.portfolio.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.portfolio.commands.CreatePortfolioCommand;
import com.tocea.corolla.portfolio.commands.CreatePortfolioFolderNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.services.ITreeManagementService;

@CommandHandler
@Transactional
public class CreatePortfolioFolderNodeCommandHandler implements ICommandHandler<CreatePortfolioFolderNodeCommand, FolderNode> {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public FolderNode handle(@Valid CreatePortfolioFolderNodeCommand command) {
		
		Portfolio portfolio = portfolioDAO.find();
			
		if (portfolio == null) {
			gate.dispatch(new CreatePortfolioCommand());
		}
		
		String text = command.getText();
		
		if (StringUtils.isEmpty(text)) {
			throw new InvalidTreeNodeInformationException("The text of a text node cannot be empty");
		}
		
		Integer parentID = command.getParentID();
		
		String typeID = command.getTypeID();
		
		TreeNode node = new FolderNode(text, typeID);
		
		treeManagementService.insertNode(portfolio, parentID, node);
		
		portfolioDAO.save(portfolio);
		
		return (FolderNode) node;
	}

}
