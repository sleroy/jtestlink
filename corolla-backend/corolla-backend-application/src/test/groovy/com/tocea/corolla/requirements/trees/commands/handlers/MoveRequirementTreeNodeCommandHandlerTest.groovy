package com.tocea.corolla.requirements.trees.commands.handlers;

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.RequirementNode
import com.tocea.corolla.requirements.domain.RequirementsTree
import com.tocea.corolla.requirements.trees.commands.MoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.commands.handlers.MoveRequirementTreeNodeCommandHandler;
import com.tocea.corolla.requirements.trees.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.revisions.services.IRevisionService;
import com.tocea.corolla.test.utils.FunctionalDocRule;
import com.tocea.corolla.utils.functests.FunctionalTestDoc;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.domain.TreeNode

@FunctionalTestDoc(requirementName = "MOVE_REQUIREMENT_TREE_NODE")
public class MoveRequirementTreeNodeCommandHandlerTest extends Specification {
	
	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)	
	def MoveRequirementTreeNodeCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	
	def setup() {
		handler = new MoveRequirementTreeNodeCommandHandler(
				requirementsTreeDAO : requirementsTreeDAO
		)
	}
	
	
	def "it should move a requirement tree node to the root of the tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 2
			def tree = new RequirementsTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new MoveRequirementTreeNodeCommand(branch, nodeId, null)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			notThrown(Exception.class)
			1 * requirementsTreeDAO.save(tree)
			
		then:
			tree.nodes.size() == 2
			tree.nodes[1].id == nodeId
			tree.nodes[0].nodes.isEmpty()
			
	}
	
	def "it should move a requirement tree node in a tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 3
			def parentId = 2
			def tree = new RequirementsTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveRequirementTreeNodeCommand(branch, nodeId, parentId)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			notThrown(Exception.class)
			1 * requirementsTreeDAO.save(tree)
			
		then:
			tree.nodes.size() == 1
			tree.nodes[0].nodes.size() == 1
			tree.nodes[0].nodes[0].nodes.size() == 1
			tree.nodes[0].nodes[0].nodes[0].id == nodeId		
			
	}
	
	def "it should move a requirement tree node that has children in a tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 1
			def parentId = 3
			def tree = new RequirementsTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveRequirementTreeNodeCommand(branch, nodeId, parentId)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			notThrown(Exception.class)
			1 * requirementsTreeDAO.save(tree)
			
		then:
			tree.nodes.size() == 1
			tree.nodes[0].nodes.size() == 1		
			tree.nodes[0].nodes[0].nodes.size() == 1		
			tree.nodes[0].id == 3
			tree.nodes[0].nodes[0].id == 1
			tree.nodes[0].nodes[0].nodes[0].id == 2
					
	}
	
	def "it should not move a tree node inside one of its children"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def nodeId = 1
			def parentId = 2
			def tree = new RequirementsTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveRequirementTreeNodeCommand(branch, nodeId, parentId)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			0 * requirementsTreeDAO.save(tree)
			thrown(InvalidRequirementsTreeInformationException.class)	
		
	}
	
	/*
	def "it should insert a new requirement tree node in a non empty tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def req_id = "2"
			def tree = new RequirementsTree(nodes: [new TreeNode(id: 1, nodes: [])])
		
		when:
			handler.handle new CreateRequirementTreeNodeCommand(branch, req_id, null)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			notThrown(Exception.class)
			1 * requirementsTreeDAO.save(tree)
			
		then:
			tree.nodes.size() == 2
			tree.nodes[1].requirementId == req_id
			tree.nodes[1].id == 2
		
	}
	
	def "it should insert a new requirement tree node inside another node of the tree"() {
		
		given:
			def branch = new ProjectBranch(id: "1")
			def req_id = "2"
			def tree = new RequirementsTree(nodes: [new TreeNode(id: 1, nodes: [])])
		
		when:
			handler.handle new CreateRequirementTreeNodeCommand(branch, req_id, 1)
	
		then:
			requirementsTreeDAO.findByBranchId(branch.id) >> tree
			notThrown(Exception.class)
			1 * requirementsTreeDAO.save(tree)
			
		then:
			tree.nodes.size() == 1
			tree.nodes[0].nodes.size() == 1
			tree.nodes[0].nodes[0].requirementId == req_id
			tree.nodes[0].nodes[0].id == 2
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
		
	}*/
}
