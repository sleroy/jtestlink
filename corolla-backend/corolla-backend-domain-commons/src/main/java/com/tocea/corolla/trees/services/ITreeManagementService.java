package com.tocea.corolla.trees.services;

import java.util.Collection;

import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.predicates.ITreeNodePredicate;

public interface ITreeManagementService {
	
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

	/**
	 * Find a specific node in a tree
	 * @param tree
	 * @param predicate
	 * @return
	 */
	public TreeNode findNode(ITree tree, ITreeNodePredicate predicate);
	
	/**
	 * Find a specific node in a collection of nodes
	 * @param nodes
	 * @param predicate
	 * @return
	 */
	public TreeNode findNode(Collection<TreeNode> nodes, ITreeNodePredicate predicate);
	
}
