package com.tocea.corolla.requirements.trees.commands.handlers;

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.trees.commands.MoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.commands.handlers.MoveRequirementTreeNodeCommandHandler;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.revisions.services.IRevisionService;
import com.tocea.corolla.test.utils.FunctionalDocRule;
import com.tocea.corolla.utils.functests.FunctionalTestDoc;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.domain.TreeNode
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.trees.services.TreeManagementService;


@FunctionalTestDoc(requirementName = "MOVE_REQUIREMENT_TREE_NODE")
public class MoveRequirementTreeNodeCommandHandlerTest extends Specification {
	
	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)	
	def MoveRequirementTreeNodeCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	def Gate gate = Mock(Gate)
	def ITreeManagementService treeManagementService = Mock(TreeManagementService)
	
	def setup() {
		handler = new MoveRequirementTreeNodeCommandHandler(
				requirementsTreeDAO : requirementsTreeDAO,
				treeManagementService : treeManagementService
		)
	}
	
	
	def "it should invoke the command to move a node in the tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 2
			def newParentId = 1
			def tree = new RequirementsTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new MoveRequirementTreeNodeCommand(branch, nodeId, newParentId)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			
		then:
			notThrown(Exception.class)
		
		then:
			1 * treeManagementService.moveNode(tree, nodeId, newParentId)
			
		then:
			1 * requirementsTreeDAO.save(_)
			
	}
	
	def "it should throw an exception if the branch is not defined"() {
		
		given:
			def branch = null
			def nodeId = 2
			def newParentId = 1
			def tree = new RequirementsTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new MoveRequirementTreeNodeCommand(branch, nodeId, newParentId)
	
		then:
			thrown(MissingProjectBranchInformationException.class)
		
	}
	
	def "it should throw an exception if the tree cannot be found"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 2
			def newParentId = 1
			def tree = null
		
		when:
			handler.handle new MoveRequirementTreeNodeCommand(branch, nodeId, newParentId)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			thrown(RequirementsTreeNotFoundException.class)
		
	}
	
}