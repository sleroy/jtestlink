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
import com.tocea.corolla.requirements.trees.domain.RequirementFolderNode
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementTreeNodeNotFoundException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.requirements.trees.predicates.FindNodeByRequirementIDPredicate;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
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
			def node_id = 1
			def tree = new RequirementsTree(nodes: [new RequirementNode(id: 1, requirementId: "1", nodes: [])])
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, node_id)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			
		then:
			treeManagementService.findNode(tree, { it instanceof FindNodeByIDPredicate }) >> tree.nodes[0]
			
		then:
			notThrown(Exception.class)
			
		then:
			1 * treeManagementService.removeNode(tree, 1)
				
		then:
			1 * requirementsTreeDAO.save(_)
		
	}
	
	def "it should invoke the command to delete the requirement if the node is a requirement node"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def node_id = 1
			def tree = new RequirementsTree(nodes: [new RequirementNode(id: 1, requirementId: "1", nodes: [])])
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, node_id)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			treeManagementService.findNode(tree, { it instanceof FindNodeByIDPredicate }) >> tree.nodes[0]
			
		then:
			notThrown(Exception.class)
			1 * gate.dispatch { it instanceof DeleteRequirementCommand && it.requirementID == tree.nodes[0].requirementId }
				
	}
	
	def "it should invoke the command to delete all the requirements in a folder"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeID = 2
			def tree = new RequirementsTree(nodes: [
			                    new RequirementNode(id: 1, requirementId: "1", nodes: []),
			                    new RequirementFolderNode(id: 2, nodes: [
			                             new RequirementNode(id: 3, requirementId: "2", nodes: []), 
			                             new RequirementNode(id: 4, requirementId: "3", nodes: []),
			                    ])
			           ]
			)
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, nodeID)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			treeManagementService.findNode(tree, { it instanceof FindNodeByIDPredicate }) >> tree.nodes[1]
			
		then:
			notThrown(Exception.class)
			0 * gate.dispatch { it instanceof DeleteRequirementCommand && it.requirementID == "1" }
			1 * gate.dispatch { it instanceof DeleteRequirementCommand && it.requirementID == "2" }
			1 * gate.dispatch { it instanceof DeleteRequirementCommand && it.requirementID == "3" }
				
	}
	
	def "it should throw an exception if the given node ID is not found in the tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def node_id = 12
			def tree = new RequirementsTree(nodes: [new RequirementNode(id: 1, requirementId: "1", nodes: [])])
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, node_id)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree		
					
		then:
			0 * gate.dispatch(_)
			0* requirementsTreeDAO.save(_)
			
		then:
			thrown(RequirementTreeNodeNotFoundException.class)
		
	}
	
	def "it should throw an exception if the branch is null"() {
		
		given:
			def branch = null
			def node_id = 1
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, node_id)
	
		then:
			0 * requirementsTreeDAO.save(_)
		
		then:
			thrown(MissingProjectBranchInformationException.class)
			
	}
	
	def "it should throw an exception if the node ID is missing"() {
					
		given:
			def nodeID = null
			def branch = new ProjectBranch(id: "1")
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, nodeID)
	
		then:
			0 * requirementsTreeDAO.save(_)
			
		then:
			thrown(MissingTreeNodeInformationException.class)
			
	}
	
	
	def "it should throw an exception if the requirement tree cannot be retrieved"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeID = 2
		
		when:
			handler.handle new RemoveRequirementTreeNodeCommand(branch, nodeID)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> null
			
		then:
			0 * requirementsTreeDAO.save(_)
			
		then:
			thrown(RequirementsTreeNotFoundException.class)
		
	}
	
}