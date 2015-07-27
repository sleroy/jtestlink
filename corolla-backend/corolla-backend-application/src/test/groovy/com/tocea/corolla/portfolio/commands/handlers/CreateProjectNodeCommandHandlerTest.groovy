package com.tocea.corolla.portfolio.commands.handlers

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.portfolio.commands.CreatePortfolioCommand
import com.tocea.corolla.portfolio.commands.CreateProjectNodeCommand
import com.tocea.corolla.portfolio.dao.IPortfolioDAO
import com.tocea.corolla.portfolio.domain.Portfolio
import com.tocea.corolla.portfolio.domain.ProjectNode
import com.tocea.corolla.portfolio.exceptions.ProjectNodeAlreadyExistException;
import com.tocea.corolla.products.exceptions.MissingProjectInformationException;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_PROJECT_NODE")
class CreateProjectNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IPortfolioDAO portfolioDAO = Mock(IPortfolioDAO)	
	def CreateProjectNodeCommandHandler handler
	def Gate gate = Mock(Gate)
	
	def setup() {
		handler = new CreateProjectNodeCommandHandler(
				portfolioDAO : portfolioDAO,
				gate : gate
		)
	}
	
	def "it should invoke the command to create a new tree node in the portfolio"() {
		
		given:
			def projectID = "1"
			def parentID = 2
			def portfolio = new Portfolio(nodes: [])
		
		when:
			handler.handle new CreateProjectNodeCommand(projectID, parentID)
	
		then:
			notThrown(Exception.class)
			
		then:
			portfolioDAO.find() >> portfolio
			
		then:
			1 * gate.dispatch { 
				it instanceof CreateTreeNodeCommand && it.tree == portfolio && it.node.projectId == projectID && it.parentID == parentID
			}
				
		then:
			1 * portfolioDAO.save(_)
		
	}
	
	def "it should create a portfolio if it is not defined yet"() {
		
		given:
			def projectID = "1"
			def parentID = null
			def portfolio = null
		
		when:
			handler.handle new CreateProjectNodeCommand(projectID, parentID)
	
		then:
			notThrown(Exception.class)
			
		then:
			portfolioDAO.find() >> portfolio
			
		then:
			1 * gate.dispatch { it instanceof CreatePortfolioCommand } >> new Portfolio(nodes: [])
		
	}
	
	def "it should throw an exception if the project ID is not defined"() {
		
		given:
			def projectID = ""
			def parentID = 2
		
		when:
			handler.handle new CreateProjectNodeCommand(projectID, parentID)
	
		then:
			thrown(MissingProjectInformationException.class)
			
	}
	
	def "it should throw an exception if a node attached to this project already exists in the portfolio"() {
		
		given:
			def projectID = "1"
			def parentID = 2
			def portfolio = new Portfolio(nodes: [new ProjectNode(id: 1, projectId: "1", nodes: [])])
		
		when:
			handler.handle new CreateProjectNodeCommand(projectID, parentID)
	
		then:
			portfolioDAO.find() >> portfolio
	
		then:
			thrown(ProjectNodeAlreadyExistException.class)
		
	}
	
}