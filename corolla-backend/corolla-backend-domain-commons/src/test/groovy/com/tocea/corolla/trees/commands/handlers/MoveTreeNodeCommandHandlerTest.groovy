package com.tocea.corolla.trees.commands.handlers

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand
import com.tocea.corolla.trees.commands.MoveTreeNodeCommand
import com.tocea.corolla.trees.commands.RemoveTreeNodeCommand
import com.tocea.corolla.trees.domain.ITree
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "MOVE_TREE_NODE")
class MoveTreeNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def MoveTreeNodeCommandHandler handler
	def Gate gate = Mock(Gate)
	
	class BasicTree implements ITree {
		def Collection<TreeNode> nodes;
	}
	
	def setup() {
		handler = new MoveTreeNodeCommandHandler(gate : gate)
	}
	
	def "it should move a tree node to the root of the tree"() {
		
		given:
			def nodeID = 2
			def newParentID = null
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new MoveTreeNodeCommand(tree, nodeID, newParentID)
	
		then:
			notThrown(Exception.class)
			
		then:
			gate.dispatch { 
				it instanceof RemoveTreeNodeCommand && it.tree == tree && it.nodeID == nodeID 
			} >> tree
			gate.dispatch { 
				it instanceof CreateTreeNodeCommand && it.tree == tree && it.node == tree.nodes[0].nodes[0] && it.parentID == newParentID
			} >> tree
			
//		then:
//			tree.nodes.size() == 2
//			tree.nodes[1].id == nodeID
//			tree.nodes[0].nodes.isEmpty()
			
	}
	
	def "it should move a tree node in a tree"() {
		
		given:
			def nodeID = 3
			def newParentID = 2
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveTreeNodeCommand(tree, nodeID, newParentID)
	
		then:
			notThrown(Exception.class)
		
		then:
			gate.dispatch { 
				it instanceof RemoveTreeNodeCommand && it.tree == tree && it.nodeID == nodeID
			} >> tree
			gate.dispatch { 
				it instanceof CreateTreeNodeCommand && it.tree == tree && it.node == tree.nodes[1] && it.parentID == newParentID
			} >> tree

//		then:
//			tree.nodes.size() == 1
//			tree.nodes[0].nodes.size() == 1
//			tree.nodes[0].nodes[0].nodes.size() == 1
//			tree.nodes[0].nodes[0].nodes[0].id == nodeID		
			
	}
	
	def "it should move a tree node that has children in a tree"() {
		
		given:
			def nodeID = 1
			def newParentID = 3
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveTreeNodeCommand(tree, nodeID, newParentID)
	
		then:
			notThrown(Exception.class)
		
		then:
			gate.dispatch { 
				it instanceof RemoveTreeNodeCommand && it.tree == tree && it.nodeID == nodeID
			} >> tree
			gate.dispatch { 
				it instanceof CreateTreeNodeCommand && it.tree == tree && it.node == tree.nodes[0] && it.parentID == newParentID
			} >> tree
	
//		then:
//			tree.nodes.size() == 1
//			tree.nodes[0].nodes.size() == 1		
//			tree.nodes[0].nodes[0].nodes.size() == 1		
//			tree.nodes[0].id == 3
//			tree.nodes[0].nodes[0].id == 1
//			tree.nodes[0].nodes[0].nodes[0].id == 2
					
	}
	
	
	def "it should not move a tree node inside one of its children"() {
		
		given:
			def nodeID = 1
			def newParentID = 2
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveTreeNodeCommand(tree, nodeID, newParentID)
	
		then:
			thrown(InvalidTreeNodeInformationException.class)	
		
	}
	
	def "it should throw an exception if the tree is not defined"() {
		
		given:
			def nodeID = 1
			def newParentID = 2
			def tree = null
		
		when:
			handler.handle new MoveTreeNodeCommand(tree, nodeID, newParentID)
	
		then:
			thrown(MissingTreeInformationException.class)	
		
	}
	
	def "it should throw an exception if the node ID is not defined"() {
		
		given:
			def nodeID = null
			def newParentID = 2
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveTreeNodeCommand(tree, nodeID, newParentID)
	
		then:
			thrown(MissingTreeNodeInformationException.class)
		
	}
	
	def "it should throw an exception if the node ID is invalid"() {
		
		given:
			def nodeID = 5
			def newParentID = 2
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveTreeNodeCommand(tree, nodeID, newParentID)
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
		
	}
	
	def "it should throw an exception if the new parent node ID is invalid"() {
		
		given:
			def nodeID = 2
			def newParentID = 5
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TreeNode(id: 2, nodes: [])]
					        ),
					        new TreeNode(id: 3, nodes: [])
					]
			)
		
		when:
			handler.handle new MoveTreeNodeCommand(tree, nodeID, newParentID)
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
		
	}
	
}