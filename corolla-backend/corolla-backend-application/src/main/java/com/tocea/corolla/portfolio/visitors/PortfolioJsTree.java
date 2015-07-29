package com.tocea.corolla.portfolio.visitors;

import java.util.Collection;

import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.portfolio.utils.PortfolioUtils;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.utils.ProjectUtils;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.visitors.JsTreeNodeVisitor;

public class PortfolioJsTree extends JsTreeNodeVisitor {

	private Collection<Project> projects;
	
	public PortfolioJsTree(Collection<Project> projects) {
		super();
		this.projects = projects;
	}
	
	@Override
	public void visit(TreeNode node) {
		
		super.visit(node);
		
		if (PortfolioUtils.isProjectNode(node)) {
			
			String ID = ((ProjectNode) node).getProjectId();
			result.getA_attr().put("data-projectID", ID);
			result.setIcon("http://lorempixel.com/16/16/");

			if (this.projects != null) {
				Project project = ProjectUtils.findByID(ID, projects);
				if (project != null) {
					result.setText(project.getName());
					result.getA_attr().put("data-key", project.getKey());
				}
			}
			
		}
		
	}
	
	@Override
	protected JsTreeNodeVisitor getChildVisitor() {		
		return new PortfolioJsTree(projects);		
	}
	
}
