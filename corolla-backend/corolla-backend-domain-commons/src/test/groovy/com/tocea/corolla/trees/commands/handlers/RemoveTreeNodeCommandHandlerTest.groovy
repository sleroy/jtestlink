package com.tocea.corolla.trees.commands.handlers;

import java.util.Collection;

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.commands.RemoveTreeNodeCommand
import com.tocea.corolla.trees.domain.ITree
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "REMOVE_TREE_NODE")
public class RemoveTreeNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def RemoveTreeNodeCommandHandler handler
	
	class BasicTree implements ITree {
		def Collection<TreeNode> nodes;
	}
	
	def setup() {
		handler = new RemoveTreeNodeCommandHandler()
	}
	
	def "it should remove a leaf node in a tree"() {
		
		given:		
			def nodeID = 1
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(id: 1, nodes: []), 
					        new TreeNode(id: 2, nodes: [])
					]
			)
		
		when:
			tree = handler.handle new RemoveTreeNodeCommand(tree, nodeID)
	
		then:
			notThrown(Exception.class)
			tree.nodes.size() == 1
			tree.nodes[0].id == 2
		
	}
	
	def "it should remove a node that contains children in a tree"() {
		
		given:		
			def nodeID = 1
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [
					        		        new TreeNode(id: 3, nodes: [])
					        		]
					        ), 
					        new TreeNode(id: 2, nodes: [])
					]
			)
		
		when:
			tree = handler.handle new RemoveTreeNodeCommand(tree, nodeID)
	
		then:
			notThrown(Exception.class)
			tree.nodes.size() == 1
			tree.nodes[0].id == 2
		
	}
	
	def "it should throw an exception if the tree is not defined"() {
		
		given:		
			def nodeID = 1
			def tree = null
		
		when:
			handler.handle new RemoveTreeNodeCommand(tree, nodeID)
	
		then:
			thrown(MissingTreeInformationException.class)
		
	}
	
	def "it should throw an exception if the node id is null"() {
		
		given:		
			def nodeID = null
			def tree = new BasicTree(nodes: [new TreeNode(id: 1, nodes: [])])
		
		when:
			handler.handle new RemoveTreeNodeCommand(tree, nodeID)
	
		then:
			thrown(MissingTreeNodeInformationException.class)
		
	}
	
	def "it should throw an exception if the node id is invalid"() {
		
		given:		
			def nodeID = 2
			def tree = new BasicTree(nodes: [new TreeNode(id: 1, nodes: [])])
		
		when:
			handler.handle new RemoveTreeNodeCommand(tree, nodeID)
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
		
	}
	
}