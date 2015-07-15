package com.tocea.corolla.requirements.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.commands.CreateRequirementTreeNodeCommand
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.RequirementNode
import com.tocea.corolla.requirements.domain.RequirementsTree
import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_REQUIREMENT_TREE_NODE")
class CreateRequirementTreeNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)	
	def CreateRequirementTreeNodeCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	def Gate gate = Mock(Gate)
	
	def setup() {
		handler = new CreateRequirementTreeNodeCommandHandler(
				requirementsTreeDAO : requirementsTreeDAO,
				gate : gate
		)
	}
	
	def "it should invoke the command to create a new tree node in the requirements tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def req_id = "2"
			def parentID = null
			def tree = new RequirementsTree(nodes: [])
		
		when:
			handler.handle new CreateRequirementTreeNodeCommand(branch, req_id, parentID)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			notThrown(Exception.class)
			1 * gate.dispatch { it instanceof CreateTreeNodeCommand && it.tree == tree && it.node.requirementId == req_id && it.parentID == parentID }
				
		then:
			1 * requirementsTreeDAO.save(_)
		
	}
	
	def "it should throw an exception if the branch is null"() {
		
		given:
			def branch = null
			def req_id = "2"
		
		when:
			handler.handle new CreateRequirementTreeNodeCommand(null, req_id, null)
	
		then:
			0 * requirementsTreeDAO.save(_)
			thrown(MissingProjectBranchInformationException.class)
			
	}
	
	def "it should throw an exception if the requirement ID is null or empty"(req_id) {
					
		given:
			def branch = new ProjectBranch(id: "1")
		
		when:
			handler.handle new CreateRequirementTreeNodeCommand(branch, req_id, null)
	
		then:
			0 * requirementsTreeDAO.save(_)
			thrown(InvalidRequirementsTreeInformationException.class)
			
		where:
			req_id << [null, ""]
			
	}
	
	def "it should not insert twice the same requirement ID in the tree"(req_id) {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def tree = new RequirementsTree(
					nodes: [
					        new RequirementNode(id: 1, requirementId: "2", nodes: []),
					        new TreeNode(
					        		id: 2, 
					        		nodes: [
					        		      new RequirementNode(id: 3, requirementId: "3", nodes: []),
					        		      new TreeNode(
					        		    		  id: 4, 
					        		    		  nodes: [new RequirementNode(id: 5, requirementId: "4", nodes: [])]
					        		      )
					        ])
					]
			)
	
		when:
			handler.handle new CreateRequirementTreeNodeCommand(branch, req_id, null)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			0 * requirementsTreeDAO.save(_)
			thrown(RequirementTreeNodeAlreadyExistException.class)
			
		where:
			req_id << ["2", "3", "4"]
		
	}
	
	def "it should throw an exception if the requirement tree cannot be retrieved"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def req_id = "2"
		
		when:
			handler.handle new CreateRequirementTreeNodeCommand(branch, req_id, null)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> null
			0 * requirementsTreeDAO.save(_)
			thrown(RequirementsTreeNotFoundException.class)
		
	}
	
}