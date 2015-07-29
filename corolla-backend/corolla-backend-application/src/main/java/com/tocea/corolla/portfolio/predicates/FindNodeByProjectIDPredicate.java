package com.tocea.corolla.portfolio.predicates;

import java.util.function.Predicate;

import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.portfolio.utils.PortfolioUtils;
import com.tocea.corolla.trees.domain.TreeNode;

public class FindNodeByProjectIDPredicate implements Predicate<TreeNode> {

	private String projectID;
	
	public FindNodeByProjectIDPredicate(String projectID) {
		this.projectID = projectID;
	}
	
	@Override
	public boolean test(TreeNode node) {
		
		return PortfolioUtils.isProjectNode(node) && (((ProjectNode)node).getProjectId().equals(projectID));
	}
	
}
