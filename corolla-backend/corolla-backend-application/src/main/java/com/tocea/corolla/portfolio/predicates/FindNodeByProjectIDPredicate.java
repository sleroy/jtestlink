package com.tocea.corolla.portfolio.predicates;

import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.portfolio.utils.PortfolioUtils;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.predicates.ITreeNodePredicate;

public class FindNodeByProjectIDPredicate implements ITreeNodePredicate {

	private String projectID;
	
	public FindNodeByProjectIDPredicate(String projectID) {
		this.projectID = projectID;
	}
	
	@Override
	public boolean test(TreeNode node) {
		
		return PortfolioUtils.isProjectNode(node) && (((ProjectNode)node).getProjectId().equals(projectID));
	}
	
}
