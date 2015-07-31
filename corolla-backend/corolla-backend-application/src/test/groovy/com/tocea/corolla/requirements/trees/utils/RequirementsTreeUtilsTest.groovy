package com.tocea.corolla.requirements.trees.utils;

import java.util.Collection;

import org.junit.Rule;

import com.tocea.corolla.requirements.trees.domain.RequirementNode
import com.tocea.corolla.trees.domain.TreeNode;

import spock.lang.Specification;


public class RequirementsTreeUtilsTest extends Specification {
	
	def "it should indicate if a node is a RequirementNode"() {
		
		given:
			def node = new RequirementNode(id: 2, requirementId: "R2", nodes:[])
	
		when:
			def ok = RequirementsTreeUtils.isRequirementNode node
			
		then:
			notThrown(Exception.class)
			
		then:
			ok
		
	}
	
	def "it should extract all the requirement nodes in a given tree"() {
		
		given:
			def nodes = [
			             new RequirementNode(
			            		 id: 1, 
			            		 requirementId: "R1", 
			            		 nodes:[
			            		        new RequirementNode(id: 2, requirementId: "R2", nodes:[]),
			            		        new TreeNode(id: 3, nodes:[])
			            		 ]
			             )
			]
					
		when:
			def result = RequirementsTreeUtils.getRequirementsNodes nodes
			
		then:
			notThrown(Exception.class)
			
		then:
			result != null
			result.size() == 2
			result.contains nodes[0]
			result.contains nodes[0].nodes[0]
		
	}
	
	def "it should extract all the IDs of the requirement nodes in a given tree"() {
		
		given:
			def nodes = [
			             new RequirementNode(
			            		 id: 1, 
			            		 requirementId: "R1", 
			            		 nodes:[
			            		        new RequirementNode(id: 2, requirementId: "R2", nodes:[]),
			            		        new TreeNode(id: 3, nodes:[])
			            		 ]
			             )
			]
					
		when:
			def result = RequirementsTreeUtils.getRequirementsIDs nodes
			
		then:
			notThrown(Exception.class)
			
		then:
			result != null
			result.size() == 2
			result.contains "R1"
			result.contains "R2"
		
	}
	
}