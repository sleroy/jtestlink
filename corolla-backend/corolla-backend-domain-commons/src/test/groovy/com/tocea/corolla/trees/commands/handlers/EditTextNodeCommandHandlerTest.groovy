package com.tocea.corolla.trees.commands.handlers

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.trees.commands.EditTextNodeCommand
import com.tocea.corolla.trees.domain.ITree
import com.tocea.corolla.trees.domain.TextNode
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "EDIT_TEXT_NODE")
class EditTextNodeCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def EditTextNodeCommandHandler handler
	
	class BasicTree implements ITree {
		def Collection<TreeNode> nodes;
	}
	
	def setup() {
		handler = new EditTextNodeCommandHandler()
	}
	
	def "it should edit the text of a text node"() {
		
		given:
			def nodeID = 2
			def text = "awesome text"
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TextNode(id: 2, text: "not so awesome text", nodes: [])]
					        )
					]
			)
		
		when:
			def result = handler.handle new EditTextNodeCommand(tree, nodeID, text)
	
		then:
			notThrown(Exception.class)
			
		then:
			result.nodes[0].nodes[0].text == text
			
	}
	
	def "it should throw an exception if the tree is not defined"() {
		
		given:
			def nodeID = 2
			def text = "awesome text"
			def tree = null
		
		when:
			handler.handle new EditTextNodeCommand(tree, nodeID, text)
	
		then:
			thrown(MissingTreeInformationException.class)
		
	}
	
	def "it should throw an exception if the node ID is missing"() {
		
		given:
			def nodeID = null
			def text = "awesome text"
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TextNode(id: 2, text: "not so awesome text", nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new EditTextNodeCommand(tree, nodeID, text)
	
		then:
			thrown(MissingTreeNodeInformationException.class)
			
	}
	
	def "it should throw an exception if the given node ID is invalid"() {
		
		given:
			def nodeID = 58
			def text = "awesome text"
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TextNode(id: 2, text: "not so awesome text", nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new EditTextNodeCommand(tree, nodeID, text)
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
			
	}
	
	def "it should throw an exception if the node is not a text node"() {
		
		given:
			def nodeID = 1
			def text = "awesome text"
			def tree = new BasicTree(
					nodes: [
					        new TreeNode(
					        		id: 1, 
					        		nodes: [new TextNode(id: 2, text: "not so awesome text", nodes: [])]
					        )
					]
			)
		
		when:
			handler.handle new EditTextNodeCommand(tree, nodeID, text)
	
		then:
			thrown(InvalidTreeNodeInformationException.class)
		
	}
	
}