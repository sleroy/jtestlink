package com.tocea.corolla.portfolio.utils;

import java.util.Collection;

import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.trees.domain.TreeNode;

public class PortfolioUtils {

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
	
}
