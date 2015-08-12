package com.tocea.corolla.portfolio.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.portfolio.commands.CreatePortfolioCommand;
import com.tocea.corolla.portfolio.commands.CreateProjectNodeCommand;
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio;
import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.portfolio.exceptions.ProjectNodeAlreadyExistException;
import com.tocea.corolla.portfolio.predicates.FindNodeByProjectIDPredicate;
import com.tocea.corolla.products.exceptions.MissingProjectInformationException;
import com.tocea.corolla.trees.services.ITreeManagementService;

@CommandHandler
@Transactional
public class CreateProjectNodeCommandHandler implements ICommandHandler<CreateProjectNodeCommand, Portfolio> {

	@Autowired
	private IPortfolioDAO portfolioDAO;
	
	@Autowired
	private Gate gate;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public Portfolio handle(@Valid CreateProjectNodeCommand command) {
		
		String projectID = command.getProjectID();
		
		if (StringUtils.isEmpty(projectID)) {
			throw new MissingProjectInformationException("No project ID found");
		}
		
		Integer parentID = command.getParentID();
		
		Portfolio portfolio = portfolioDAO.find();
		
		if (portfolio == null) {		
			portfolio = gate.dispatch(new CreatePortfolioCommand());		
		}
		
		ProjectNode sameNode = (ProjectNode) treeManagementService.findNode(portfolio, new FindNodeByProjectIDPredicate(projectID));

		if (sameNode != null) {
			throw new ProjectNodeAlreadyExistException();
		}
		
		ProjectNode projectNode = new ProjectNode(projectID);
		
		treeManagementService.insertNode(portfolio, parentID, projectNode);
		
		portfolioDAO.save(portfolio);
		
		return portfolio;
	}

}