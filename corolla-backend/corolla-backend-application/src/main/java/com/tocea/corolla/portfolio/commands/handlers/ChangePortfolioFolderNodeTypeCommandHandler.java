package com.tocea.corolla.portfolio.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.portfolio.commands.ChangePortfolioFolderNodeTypeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.exceptions.PortfolioNotFoundException;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidFolderNodeTypeInformationException;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
@Transactional
public class ChangePortfolioFolderNodeTypeCommandHandler implements ICommandHandler<ChangePortfolioFolderNodeTypeCommand, FolderNode> {

	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public FolderNode handle(@Valid ChangePortfolioFolderNodeTypeCommand command) {

		Portfolio portfolio = portfolioDAO.find();
		
		if (portfolio == null) {
			throw new PortfolioNotFoundException();
		}
		
		Integer nodeID = command.getNodeID();
		
		if (nodeID == null) {
			throw new MissingTreeNodeInformationException("No node ID found");
		}
		
		TreeNode node = treeManagementService.findNode(portfolio, new FindNodeByIDPredicate(nodeID));
		
		if (node == null) {
			throw new InvalidTreeNodeInformationException("No node found for this ID");
		}	
		
		if (!TreeNodeUtils.isFolderNode(node)) {
			throw new InvalidTreeNodeInformationException("This is not a folder node");
		}
		
		String typeID = command.getTypeID();
		
		FolderNodeType folderNodeType = folderNodeTypeDAO.findOne(typeID);
		
		if (folderNodeType == null) {
			throw new InvalidFolderNodeTypeInformationException("No folder node type found for the ID "+typeID);
		}
		
		((FolderNode)node).setTypeID(typeID);
		
		portfolioDAO.save(portfolio);
		
		return (FolderNode) node;
	}

}
