package com.tocea.corolla.portfolio.utils;

import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.trees.domain.TreeNode;

public class PortfolioUtils {

	/**
	 * Tests if a node is a ProjectNode
	 * @param node
	 * @return
	 */
	public static boolean isProjectNode(TreeNode node) {
		return node.getClass().equals(ProjectNode.class);
	}
	
	/**
	 * Retrieves a Project node by its project ID
	 * @param projectID
	 * @param nodes
	 * @return
	 */
	public static ProjectNode findNodeByProjectId(final String projectID, Collection<TreeNode> nodes) {
		
		for(TreeNode node : nodes) {
			if (node.getClass().equals(ProjectNode.class) && (((ProjectNode)node).getProjectId().equals(projectID))) {
				return (ProjectNode) node;
			}
			ProjectNode child = findNodeByProjectId(projectID, node.getNodes());
			if (child != null) {
				return child;
			}
		}
		
		return null;
	}
	
	/**
	 * Retrieves the list of project IDs in the given nodes
	 * @param nodes
	 * @return
	 */
	public static List<String> getProjectIDs(Collection<TreeNode> nodes) {
		
		List<String> ids = Lists.newArrayList();
		
		for(TreeNode node : nodes) {
			
			if (isProjectNode(node)) {
				ids.add(((ProjectNode)node).getProjectId());
			}
			
			ids.addAll(getProjectIDs(node.getNodes()));
			
		}
		
		return ids;
	}
	
}