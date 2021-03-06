package com.tocea.corolla.trees.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
import com.tocea.corolla.trees.predicates.ITreeNodePredicate;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@Service
public class TreeManagementService implements ITreeManagementService {

	private TreeNode findNodeByID(Collection<TreeNode> nodes, Integer nodeID) {
		
		return findNode(nodes, new FindNodeByIDPredicate(nodeID));
	}
	
	@Override
	public ITree insertNode(ITree tree, Integer parentID, TreeNode newNode) {
		
		if (tree == null) {
			throw new MissingTreeInformationException("No tree found");
		}
		
		if (newNode == null) {
			throw new MissingTreeNodeInformationException("The tree node to insert is null");
		}
		
		Collection<TreeNode> nodes = Lists.newArrayList(tree.getNodes());

		if (newNode.getId() == null) {
			newNode.setId(TreeNodeUtils.getMaxNodeId(nodes)+1);
		}
		
		if (newNode.getNodes() == null) {
			newNode.setNodes((Collection<TreeNode>) new ArrayList<TreeNode>());
		}
		
		if (parentID == null) {
			
			nodes.add(newNode);
			
		}else{
			
			TreeNode parentNode = findNodeByID(nodes, parentID);
			
			if (parentNode == null) {
				throw new InvalidTreeNodeInformationException("The given parent ID does not match any of the nodes of this tree.");
			}
			
			Collection<TreeNode> parentNodes = Lists.newArrayList(parentNode.getNodes());
			parentNodes.add(newNode);
			parentNode.setNodes(parentNodes);
			
		}
		
		tree.setNodes(nodes);
		
		return tree;
	}

	@Override
	public ITree moveNode(ITree tree, Integer nodeID, Integer newParentID) {
		
		if (tree == null) {
			throw new MissingTreeInformationException("No tree found");
		}
		
		if (nodeID == null) {
			throw new MissingTreeNodeInformationException("No ID found");
		}
		
		Collection<TreeNode> nodes = Lists.newArrayList(tree.getNodes());
		
		TreeNode node = findNodeByID(nodes, nodeID);
		
		if (node == null) {
			throw new InvalidTreeNodeInformationException("The given node ID does not match any node of this tree");
		}
		
		if (newParentID == null) {
			
			tree = removeNode(tree, nodeID);
			tree = insertNode(tree, newParentID, node);
			
		}else{
			
			TreeNode newParentNode = findNodeByID(nodes, nodeID);
			
			if (newParentNode == null) {
				throw new InvalidTreeNodeInformationException("The given parent node ID does not match any node of this tree");
			}
			
			if (TreeNodeUtils.hasNodeWithId(newParentID, node, true)) {
				throw new InvalidTreeNodeInformationException("Cannot move a tree node inside one of its own children");
			}
			
			tree = removeNode(tree, nodeID);
			tree = insertNode(tree, newParentID, node);
			
		}
		
		return tree;
	}

	@Override
	public ITree removeNode(ITree tree, Integer nodeID) {
				
		if (tree == null) {
			throw new MissingTreeInformationException("No tree found");
		}
		
		if (nodeID == null) {
			throw new MissingTreeNodeInformationException("No ID found");
		}
		
		Collection<TreeNode> nodes = Lists.newArrayList(tree.getNodes());
		
		TreeNode node = findNodeByID(nodes, nodeID);
		
		if (node == null) {
			throw new InvalidTreeNodeInformationException("The given node ID does not match any node of this tree");
		}
		
		TreeNode parentNode = TreeNodeUtils.getParentNodeOf(nodeID, nodes);
		
		if (parentNode == null) {
			
			nodes.remove(node);
		
		}else{
		
			parentNode.getNodes().remove(node);
			
		}
		
		tree.setNodes(nodes);
		
		return tree; 
	}
	
	@Override
	public TreeNode findNode(ITree tree, ITreeNodePredicate predicate) {
		
		return findNode(tree.getNodes(), predicate);
	}
	
	@Override
	public TreeNode findNode(Collection<TreeNode> nodes, ITreeNodePredicate predicate) {
		
		for(TreeNode node : nodes) {			
			
			if (predicate.test(node)) {
				return node;
			}
			
			TreeNode child = findNode(node.getNodes(), predicate);
			
			if (child != null) {
				return child;
			}
		}
		
		return null;
		
	}
	
}