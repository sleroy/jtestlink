package com.tocea.corolla.portfolio.commands.handlers;

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.portfolio.commands.EditPortfolioTextNodeCommand
import com.tocea.corolla.portfolio.dao.IPortfolioDAO;
import com.tocea.corolla.portfolio.domain.Portfolio
import com.tocea.corolla.portfolio.domain.ProjectNode
import com.tocea.corolla.test.utils.FunctionalDocRule;
import com.tocea.corolla.portfolio.exceptions.PortfolioNotFoundException;
import com.tocea.corolla.utils.functests.FunctionalTestDoc;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.commands.EditTextNodeCommand
import com.tocea.corolla.trees.domain.TextNode
import com.tocea.corolla.trees.domain.TreeNode

@FunctionalTestDoc(requirementName = "EDIT_PORTFOLIO_TEXT_NODE")
public class EditPortfolioTextNodeCommandHandlerTest extends Specification {
	
	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IPortfolioDAO portfolioDAO = Mock(IPortfolioDAO)	
	def EditPortfolioTextNodeCommandHandler handler
	def Gate gate = Mock(Gate)
	
	def setup() {
		handler = new EditPortfolioTextNodeCommandHandler(
				portfolioDAO : portfolioDAO,
				gate : gate
		)
	}
	
	
	def "it should invoke the command to edit a text node in the tree"() {
		
		given:
			def nodeId = 2
			def text = "my not so awesome text"
			def portfolio = new Portfolio(
					nodes: [
					        new TextNode(
					        		id: 1, 
					        		text: "my awesome text", 
					        		nodes: [new ProjectNode(
							        		id: 2, 
							        		projectId: "2", 
							        		nodes: []
							        )]
					        )
					]
			)
		
		when:
			handler.handle new EditPortfolioTextNodeCommand(nodeId, text)
	
		then:
			portfolioDAO.find() >> portfolio
			
		then:
			notThrown(Exception.class)
		
		then:
			1 * gate.dispatch { it instanceof EditTextNodeCommand && it.tree == portfolio && it.nodeID == nodeId && it.text == text }	
			
		then:
			1 * portfolioDAO.save(_)
			
	}
	
	def "it should throw an exception if the portfolio cannot be found"() {
		
		given:
			def nodeId = 2
			def text = "my not so awesome text"
			def portfolio = null
		
		when:
			handler.handle new EditPortfolioTextNodeCommand(nodeId, text)
	
		then:
			portfolioDAO.find() >> portfolio
			
		then:
			thrown(PortfolioNotFoundException.class)
		
	}
	
}