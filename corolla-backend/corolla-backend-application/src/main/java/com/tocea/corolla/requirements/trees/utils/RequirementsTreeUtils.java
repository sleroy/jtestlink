package com.tocea.corolla.requirements.trees.utils;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.trees.domain.TreeNode;

public class RequirementsTreeUtils {

	/**
	 * Retrieves a tree node by its Requirement ID
	 * @param id
	 * @param nodes
	 * @return
	 */
	public static TreeNode getNodeByRequirementId(String id, Collection<TreeNode> nodes) {
		
		for(TreeNode node : nodes) {			
			
			if (node.getClass().equals(RequirementNode.class) && ((RequirementNode)node).getRequirementId().equals(id)) {
				return node;
			}
			
			TreeNode child = getNodeByRequirementId(id, node.getNodes());
			
			if (child != null) {
				return child;
			}
		}
		
		return null;		
	}
	
	/**
	 * Returns a flat list of all the requirements nodes
	 * @param nodes
	 * @return
	 */
	public static Collection<RequirementNode> getRequirementsNodes(Collection<TreeNode> nodes) {
		
		Collection<RequirementNode> requirementNodes = Lists.newArrayList();
		
		for(TreeNode node : nodes) {
			
			if (node.getClass().equals(RequirementNode.class)) {			
				requirementNodes.add((RequirementNode)node);			
			}
			
			requirementNodes.addAll(getRequirementsNodes(node.getNodes()));
			
		}
		
		return requirementNodes;
		
	}
	
}
