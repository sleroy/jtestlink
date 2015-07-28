package com.tocea.corolla.requirements.trees.commands.handlers;

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.trees.commands.EditRequirementFolderNodeCommand
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.test.utils.FunctionalDocRule;
import com.tocea.corolla.utils.functests.FunctionalTestDoc;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.domain.FolderNode
import com.tocea.corolla.trees.domain.TreeNode
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.trees.services.TreeManagementService;


@FunctionalTestDoc(requirementName = "EDIT_REQUIREMENT_TEXT_NODE")
public class EditRequirementFolderNodeCommandHandlerTest extends Specification {
	
	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)	
	def EditRequirementFolderNodeCommandHandler handler
	def ITreeManagementService treeManagementService = Mock(TreeManagementService)
	
	def setup() {
		handler = new EditRequirementFolderNodeCommandHandler(
				requirementsTreeDAO : requirementsTreeDAO,
				treeManagementService : treeManagementService
		)
	}
	
	
	def "it should invoke the command to edit a text node in the requirements tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 1
			def text = "my not so awesome text"
			def tree = new RequirementsTree(
					nodes: [
					        new FolderNode(
					        		id: 1,
					        		text: "my awesome text",
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new EditRequirementFolderNodeCommand(branch, nodeId, text)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			
		then:
			notThrown(Exception.class)
		
		then:
			treeManagementService.findNodeByID(tree, nodeId) >> tree.nodes[0]
			
		then:
			tree.nodes[0].text == text
			
		then:
			1 * requirementsTreeDAO.save(_)
			
	}
	
	def "it should throw an exception if the branch is not defined"() {
		
		given:
			def branch = null
			def nodeId = 1
			def text = "my not so awesome text"
			def tree = new RequirementsTree(
					nodes: [
					        new FolderNode(
					        		id: 1,
					        		text: "my awesome text",
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new EditRequirementFolderNodeCommand(branch, nodeId, text)
	
		then:
			thrown(MissingProjectBranchInformationException.class)
		
	}
	
	def "it should throw an exception if the tree cannot be found"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 1
			def text = "my not so awesome text"
			def tree = null
		
		when:
			handler.handle new EditRequirementFolderNodeCommand(branch, nodeId, text)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			
		then:
			thrown(RequirementsTreeNotFoundException.class)
		
	}
	
	def "it should throw an exception if the node ID is missing"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = null
			def text = "my not so awesome text"
			def tree = new RequirementsTree(
					nodes: []
			)
		
		when:
			handler.handle new EditRequirementFolderNodeCommand(branch, nodeId, text)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
	
		then:
			thrown(MissingTreeNodeInformationException.class)
		
	}
	
	def "it should throw an exception if the node ID does not match any node of the tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 1
			def text = "my not so awesome text"
			def tree = new RequirementsTree(
					nodes: []
			)
		
		when:
			handler.handle new EditRequirementFolderNodeCommand(branch, nodeId, text)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			treeManagementService.findByNodeID(nodeId) >> null
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
		
	}
	
	def "it should throw an exception if the node is not a folder"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 1
			def text = "my not so awesome text"
			def tree = new RequirementsTree(
					nodes: [
					        new FolderNode(
					        		id: 1,
					        		text: "my awesome text",
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new EditRequirementFolderNodeCommand(branch, nodeId, text)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			
		then:
			treeManagementService.findNodeByID(tree, nodeId) >> tree.nodes[0].nodes[0]
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
		
	}
	
}