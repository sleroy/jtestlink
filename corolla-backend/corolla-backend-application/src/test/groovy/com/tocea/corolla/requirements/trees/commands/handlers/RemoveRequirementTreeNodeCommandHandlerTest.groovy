package com.tocea.corolla.requirements.trees.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.commands.DeleteRequirementCommand
import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.requirements.trees.commands.RemoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.commands.handlers.RemoveRequirementTreeNodeCommandHandler;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementTreeNodeNotFoundException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.trees.services.TreeManagementService;
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "REMOVE_REQUIREMENT_TREE_NODE")
class RemoveRequirementTreeNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)	
	def RemoveRequirementTreeNodeCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	def Gate gate = Mock(Gate)
	def ITreeManagementService treeManagementService = Mock(TreeManagementService)
	
	def setup() {
		handler = new RemoveRequirementTreeNodeCommandHandler(
				requirementsTreeDAO : requirementsTreeDAO,
				gate : gate,
				treeManagementService : treeManagementService
		)
	}
	
	def "it should invoke the service to remove a tree node in the requirements tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def req_id = "1"
			def tree = new RequirementsTree(nodes: [new RequirementNode(id: 1, requirementId: "1", nodes: [])])
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, req_id)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			
		then:
			notThrown(Exception.class)
			
		then:
			1 * treeManagementService.removeNode(tree, 1)
				
		then:
			1 * requirementsTreeDAO.save(_)
		
	}
	
	def "it should invoke the command to delete the requirement"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def req_id = "1"
			def tree = new RequirementsTree(nodes: [new RequirementNode(id: 1, requirementId: "1", nodes: [])])
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, req_id)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			notThrown(Exception.class)
			1 * gate.dispatch { it instanceof DeleteRequirementCommand && it.requirementID == req_id }
				
	}
	
	def "it should throw an exception if the given requirement ID is not found in the tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def req_id = "2"
			def tree = new RequirementsTree(nodes: [new RequirementNode(id: 1, requirementId: "1", nodes: [])])
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, req_id)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			thrown(RequirementTreeNodeNotFoundException.class)
					
		then:
			0 * gate.dispatch(_)
			0* requirementsTreeDAO.save(_)
		
	}
	
	def "it should throw an exception if the branch is null"() {
		
		given:
			def branch = null
			def req_id = "1"
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, req_id)
	
		then:
			0 * requirementsTreeDAO.save(_)
			thrown(MissingProjectBranchInformationException.class)
			
	}
	
	def "it should throw an exception if the requirement ID is null or empty"(req_id) {
					
		given:
			def branch = new ProjectBranch(id: "1")
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, req_id)
	
		then:
			0 * requirementsTreeDAO.save(_)
			thrown(MissingRequirementInformationException.class)
			
		where:
			req_id << [null, ""]
			
	}
	
	
	def "it should throw an exception if the requirement tree cannot be retrieved"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def req_id = "2"
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, req_id)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> null
			
		then:
			0 * requirementsTreeDAO.save(_)
			thrown(RequirementsTreeNotFoundException.class)
		
	}
	
}