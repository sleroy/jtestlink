package com.tocea.corolla.trees.services;

import java.util.Collection;

import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;

public interface ITreeManagementService {
	
	/**
	 * Retrieves a node by its id in a tree
	 * @param tree
	 * @param nodeID
	 * @return
	 */
	public TreeNode findNodeByID(ITree tree, Integer nodeID);
	
	/**
	 * Retrieves a node by its id in a collection of nodes
	 * @param nodes
	 * @param nodeID
	 * @return
	 */
	public TreeNode findNodeByID(Collection<TreeNode> nodes, Integer nodeID);

	/**
	 * Insert a node in a tree
	 * @param tree
	 * @param parentID
	 * @param node
	 * @return
	 */
	public ITree insertNode(ITree tree, Integer parentID, TreeNode node);
	
	/**
	 * Move a node in a tree
	 * @param tree
	 * @param nodeID
	 * @param newParentID
	 * @return
	 */
	public ITree moveNode(ITree tree, Integer nodeID, Integer newParentID);
	
	/**
	 * Remove a node in a tree
	 * @param tree
	 * @param nodeID
	 * @return
	 */
	public ITree removeNode(ITree tree, Integer nodeID);
	
}
