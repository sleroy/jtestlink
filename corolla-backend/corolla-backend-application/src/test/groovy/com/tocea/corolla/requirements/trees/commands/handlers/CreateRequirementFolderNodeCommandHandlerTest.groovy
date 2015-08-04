package com.tocea.corolla.requirements.trees.commands.handlers;

import org.hibernate.cfg.ExtendsQueueEntry;
import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.requirements.trees.commands.CreateRequirementFolderNodeCommand;
import com.tocea.corolla.requirements.trees.commands.handlers.CreateRequirementFolderNodeCommandHandler;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.services.ITreeManagementService
import com.tocea.corolla.trees.services.TreeManagementService

import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_REQUIREMENT_TEXT_NODE")
public class CreateRequirementFolderNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)	
	def IRevisionService revisionService = Mock(IRevisionService)
	def CreateRequirementFolderNodeCommandHandler handler
	def ITreeManagementService treeManagementService = Mock(TreeManagementService)
	
	def setup() {
		handler = new CreateRequirementFolderNodeCommandHandler(
				requirementsTreeDAO : requirementsTreeDAO,
				treeManagementService : treeManagementService
		)
	}
	
	def "it should invoke the service to create a new tree node in the requirements tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def parentID = null
			def text = "requirements"
			def typeID = "1"
			def tree = new RequirementsTree(nodes: [])
		
		when:
			handler.handle new CreateRequirementFolderNodeCommand(branch, parentID, text, typeID)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			
		then:
			notThrown(Exception.class)
			
		then:
			1 * treeManagementService.insertNode(tree, parentID, { it.text == text && it.typeID == typeID })
	
		then:
			1 * requirementsTreeDAO.save(_)
		
	}
	
	def "it should throw an exception if the branch is null"() {
		
		given:
			def branch = null
			def parentID = null
			def text = "requirements"
			def typeID = "1"
			def tree = new RequirementsTree(nodes: [])
		
		when:
			handler.handle new CreateRequirementFolderNodeCommand(branch, parentID, text, typeID)
	
		then:
			0 * requirementsTreeDAO.save(_)
			thrown(MissingProjectBranchInformationException.class)
			
	}
	
	def "it should throw an exception if the text is null or empty"(text) {
					
		given:
			def branch = new ProjectBranch(id: "1")
			def parentID = null
			def typeID = "1"
			def tree = new RequirementsTree(nodes: [])
		
		when:
			handler.handle new CreateRequirementFolderNodeCommand(branch, parentID, text, typeID)
	
		then:
			0 * requirementsTreeDAO.save(_)
			thrown(InvalidRequirementsTreeInformationException.class)
			
		where:
			text << [null, ""]
			
	}
	
	def "it should throw an exception if the requirement tree cannot be retrieved"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def parentID = null
			def text = "requirements"
			def typeID = "1"
			def tree = null
		
		when:
			handler.handle new CreateRequirementFolderNodeCommand(branch, parentID, text, typeID)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			
		then:
			0 * requirementsTreeDAO.save(_)
			thrown(RequirementsTreeNotFoundException.class)
		
	}
	
}