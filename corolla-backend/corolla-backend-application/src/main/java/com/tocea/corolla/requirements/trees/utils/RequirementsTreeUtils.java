package com.tocea.corolla.requirements.trees.utils;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.tocea.corolla.requirements.domain.Requirement;
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
	
	/**
	 * Removes nodes attached to the given requirements IDs
	 * @param nodes
	 * @param ids
	 * @return
	 */
	public static Collection<TreeNode> removeRequirementsByID(Collection<TreeNode> nodes, Collection<String> ids) {
			
		for(TreeNode node : Lists.newArrayList(nodes)) {			
			if (node.getClass().equals(RequirementNode.class) && ids.contains(((RequirementNode)node).getRequirementId())) {
				nodes.remove(node);					
			}else{	
				node.setNodes(removeRequirementsByID(node.getNodes(), ids));				
			}			
		}
		
		return nodes;	
	}
	
	/**
	 * Removes nodes attached to the given requirements
	 * @param nodes
	 * @param requirements
	 * @return
	 */
	public static Collection<TreeNode> removeRequirements(Collection<TreeNode> nodes, Collection<Requirement> requirements) {
		
		Collection<String> ids = Collections2.transform(requirements, new Function<Requirement, String>() {
			@Override
			public String apply(Requirement requirement) {
				return requirement.getId();
			}		
		});
		
		return (ids != null && !ids.isEmpty()) ? removeRequirementsByID(nodes, ids) : nodes;	
	}
	
	/**
	 * Removes nodes not attached to the given requirements IDs
	 * @param nodes
	 * @param ids
	 * @return
	 */
	public static Collection<TreeNode> removeRequirementsNotInIDs(Collection<TreeNode> nodes, Collection<String> ids) {
			
		for(TreeNode node : Lists.newArrayList(nodes)) {	
			if (node.getClass().equals(RequirementNode.class) && !ids.contains(((RequirementNode)node).getRequirementId())) {							
				nodes.remove(node);					
			}else{
				node.setNodes(removeRequirementsNotInIDs(node.getNodes(), ids));				
			}			
		}
		
		return nodes;	
	}
	
	/**
	 * Removes nodes not attached to the given requirements
	 * @param nodes
	 * @param requirements
	 * @return
	 */
	public static Collection<TreeNode> removeRequirementsNotIn(Collection<TreeNode> nodes, Collection<Requirement> requirements) {

		Collection<String> ids = Collections2.transform(requirements, new Function<Requirement, String>() {
			@Override
			public String apply(Requirement requirement) {
				return requirement.getId();
			}		
		});
		
		return removeRequirementsNotInIDs(nodes, ids);	
	}
	
}