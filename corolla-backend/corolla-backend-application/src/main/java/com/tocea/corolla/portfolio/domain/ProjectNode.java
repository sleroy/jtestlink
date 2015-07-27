package com.tocea.corolla.portfolio.domain;

import java.util.ArrayList;

import com.tocea.corolla.trees.domain.TreeNode;

public class ProjectNode extends TreeNode {

	private String projectId;
	
	public ProjectNode() {
		super();
	}
	
	public ProjectNode(String projectId) {
		super();
		setProjectId(projectId);
		setNodes(new ArrayList<TreeNode>());
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Override
	public TreeNode clone() {		
		return this.clone(new ProjectNode(this.projectId));		
	}
	
}
