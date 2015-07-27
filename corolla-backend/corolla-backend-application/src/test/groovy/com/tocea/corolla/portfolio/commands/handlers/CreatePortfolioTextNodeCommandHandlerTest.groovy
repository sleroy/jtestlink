package com.tocea.corolla.portfolio.commands.handlers

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.portfolio.commands.CreatePortfolioCommand
import com.tocea.corolla.portfolio.commands.CreatePortfolioTextNodeCommand
import com.tocea.corolla.portfolio.commands.CreateProjectNodeCommand
import com.tocea.corolla.portfolio.dao.IPortfolioDAO
import com.tocea.corolla.portfolio.domain.Portfolio
import com.tocea.corolla.portfolio.domain.ProjectNode
import com.tocea.corolla.portfolio.exceptions.ProjectNodeAlreadyExistException;
import com.tocea.corolla.products.exceptions.MissingProjectInformationException;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_PROJECT_NODE")
class CreatePortfolioTextNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IPortfolioDAO portfolioDAO = Mock(IPortfolioDAO)	
	def CreatePortfolioTextNodeCommandHandler handler
	def Gate gate = Mock(Gate)
	
	def setup() {
		handler = new CreatePortfolioTextNodeCommandHandler(
				portfolioDAO : portfolioDAO,
				gate : gate
		)
	}
	
	def "it should invoke the command to create a new tree node in the portfolio"() {
		
		given:
			def text = "TEXT"
			def parentID = 2
			def portfolio = new Portfolio(nodes: [])
		
		when:
			handler.handle new CreatePortfolioTextNodeCommand(text, parentID)
	
		then:
			notThrown(Exception.class)
			
		then:
			portfolioDAO.find() >> portfolio
			
		then:
			1 * gate.dispatch { 
				it instanceof CreateTreeNodeCommand && it.tree == portfolio && it.node.text == text && it.parentID == parentID
			}
				
		then:
			1 * portfolioDAO.save(_)
		
	}
	
	def "it should create a portfolio if it is not defined yet"() {
		
		given:
			def text = "TEXT"
			def parentID = 2
			def portfolio = null
		
		when:
			handler.handle new CreatePortfolioTextNodeCommand(text, parentID)
	
		then:
			notThrown(Exception.class)
			
		then:
			portfolioDAO.find() >> portfolio
			
		then:
			1 * gate.dispatch { it instanceof CreatePortfolioCommand } >> new Portfolio(nodes: [])
		
	}
	
	def "it should throw an exception if the text is empty"() {
		
		given:
			def text = ""
			def parentID = 2
		
		when:
			handler.handle new CreatePortfolioTextNodeCommand(text, parentID)
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
			
	}
	
}