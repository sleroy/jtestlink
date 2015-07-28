package com.tocea.corolla.trees.utils;

import java.util.Collection;

import com.google.common.collect.Lists;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.domain.TreeNode;

public class TreeNodeUtils {
	
	/**
	 * Retrieves a node by its id
	 * @param id
	 * @param nodes
	 * @return
	 */
	public static TreeNode getNodeById(Integer id, Collection<TreeNode> nodes) {
		
		for(TreeNode node : nodes) {
			
			if (node.getId().equals(id)) {
				return node;
			}
			
			TreeNode childNode = getNodeById(id, node.getNodes());
			
			if (childNode != null) {
				return childNode;
			}
		}
		
		return null;
	}
	
	/**
	 * Retrieves the max id of the nodes in a given tree
	 * @param nodes
	 * @return
	 */
	public static Integer getMaxNodeId(Collection<TreeNode> nodes) {
		
		int max = 0;
		
		for(TreeNode node : nodes) {
			
			if (node.getId() > max) {				
				max = node.getId();
			}
			
			int childrenId = getMaxNodeId(node.getNodes());
			
			if (childrenId > max) {
				max = childrenId;
			}
			
		}	
		
		return max;		
	}
	
	/**
	 * Retrieves the parent node of the node with the given ID
	 * @param id
	 * @param nodes
	 * @return
	 */
	public static TreeNode getParentNodeOf(Integer id, Collection<TreeNode> nodes) {
		
		for(TreeNode node : nodes) {
			
			if (hasNodeWithId(id, node, false)) {
				return node;
			}
			
			TreeNode childNode = getParentNodeOf(id, node.getNodes());
			
			if (childNode != null) {
				return childNode;
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Indicates if a node has a child matching a given id
	 * @param id
	 * @param parent
	 * @return
	 */
	public static Boolean hasNodeWithId(Integer id, TreeNode parent, Boolean recursive) {
		
		for(TreeNode node : Lists.newArrayList(parent.getNodes())) {
			
			if (node.getId().equals(id)) {
				return true;
			}
			
			if (recursive) {
				Boolean found = hasNodeWithId(id, node, true);
				if (found) {
					return true;
				}
			}
			
		}
		
		return false;		
	}
	
	/**
	 * Tests if a node is a text node
	 * @param node
	 * @return
	 */
	public static Boolean isFolderNode(TreeNode node) {
		
		return node.getClass().equals(FolderNode.class);
	}

}