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
import com.tocea.corolla.trees.commands.EditTextNodeCommand
import com.tocea.corolla.trees.domain.FolderNode
import com.tocea.corolla.trees.domain.TreeNode

@FunctionalTestDoc(requirementName = "EDIT_REQUIREMENT_TEXT_NODE")
public class EditRequirementTextNodeCommandHandlerTest extends Specification {
	
	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)	
	def EditRequirementFolderNodeCommandHandler handler
	def Gate gate = Mock(Gate)
	
	def setup() {
		handler = new EditRequirementFolderNodeCommandHandler(
				requirementsTreeDAO : requirementsTreeDAO,
				gate : gate
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
			notThrown(Exception.class)
		
		then:
			1 * gate.dispatch { it instanceof EditTextNodeCommand && it.tree == tree && it.nodeID == nodeId && it.text == text }	
			
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
	
}