package com.tocea.corolla.trees.commands.handlers

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand
import com.tocea.corolla.trees.domain.ITree
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_TREE_NODE")
class CreateTreeNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def CreateTreeNodeCommandHandler handler
	
	class BasicTree implements ITree {
		def Collection<TreeNode> nodes;
	}
	
	def setup() {
		handler = new CreateTreeNodeCommandHandler()
	}
	
	def "it should insert a new node in an empty tree"() {
		
		given:
			def tree = new BasicTree(nodes: [])
			def node = new TreeNode();
		
		when:
			handler.handle new CreateTreeNodeCommand(tree, node, null)
	
		then:
			notThrown(Exception.class)
			tree.nodes.size() == 1
			tree.nodes[0].id == 1
		
	}
	
	def "it should insert a new tree node in a non empty tree"() {
		
		given:
			def node = new TreeNode();
			def tree = new BasicTree(nodes: [new TreeNode(id: 1, nodes: [])])
		
		when:
			handler.handle new CreateTreeNodeCommand(tree, node, null)
	
		then:
			notThrown(Exception.class)
			tree.nodes.size() == 2
			tree.nodes[1].id == 2
		
	}
	
	def "it should insert a new requirement tree node inside another node of the tree"() {
		
		given:
			def node = new TreeNode();
			def parentID = 1
			def tree = new BasicTree(nodes: [new TreeNode(id: 1, nodes: [])])
		
		when:
			handler.handle new CreateTreeNodeCommand(tree, node, parentID)
	
		then:
			notThrown(Exception.class)
			tree.nodes.size() == 1
			tree.nodes[0].nodes.size() == 1
			tree.nodes[0].nodes[0].id == 2
	}
	
	def "it should throw an exception if the tree cannot be found"() {
		
		given:
			def node = new TreeNode();
			def tree = null
		
		when:
			handler.handle new CreateTreeNodeCommand(tree, node, null)
		
		then:
			thrown(MissingTreeInformationException.class)
		
	}
	
	def "it should throw an exception if the node to insert is null"() {
		
		given:
			def node = null
			def parentID = null
			def tree = new BasicTree(nodes: [])
		
		when:
			handler.handle new CreateTreeNodeCommand(tree, node, parentID)
	
		then:
			thrown(MissingTreeNodeInformationException.class)
			
	}
	
	def "it should throw an exception if the parent ID is invalid"() {
		
		given:
			def node = new TreeNode()
			def parentID = 1
			def tree = new BasicTree(nodes: [])
		
		when:
			handler.handle new CreateTreeNodeCommand(tree, node, parentID)
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
			
	}
	
}