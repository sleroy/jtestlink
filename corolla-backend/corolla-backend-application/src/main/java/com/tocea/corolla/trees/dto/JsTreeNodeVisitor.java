package com.tocea.corolla.trees.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.utils.ProjectUtils;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.trees.domain.ITreeNodeVisitor;
import com.tocea.corolla.trees.domain.TextNode;
import com.tocea.corolla.trees.domain.TreeNode;

public class JsTreeNodeVisitor implements ITreeNodeVisitor {

	private JsTreeNodeDTO result;
	
	private Collection<Project> projects;
	
	public JsTreeNodeVisitor() {
		
	}
	
	public JsTreeNodeVisitor(Collection<Project> projects) {
		setProjects(projects);
	}
	
	@Override
	public void visit(TreeNode node) {
		
		result = new JsTreeNodeDTO();
		
		if (node.getClass().equals(TextNode.class)) {
			result.setText(((TextNode)node).getText());
		}	
		
		result.setA_attr(new HashMap<String, String>());
		result.getA_attr().put("data-nodeID", node.getId().toString());
		
		if (node.getClass().equals(RequirementNode.class)) {			
			this.visitRequirementNode((RequirementNode)node);
		}
		
		if (node.getClass().equals(ProjectNode.class)) {			
			this.visitProjectNode((ProjectNode)node);
		}

		List<JsTreeNodeDTO> children = Lists.newArrayList();
		
		for(TreeNode child : node.getNodes()) {
			
			JsTreeNodeVisitor visitor = new JsTreeNodeVisitor(projects);
			child.accept(visitor);
			
			children.add(visitor.getResult());		
			
		}
		
		result.setChildren(children);
		
	}
	
	private void visitProjectNode(ProjectNode node) {
		
		String ID = ((ProjectNode)node).getProjectId();
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
	
	private void visitRequirementNode(RequirementNode node) {
		
		result.getA_attr().put("data-key", ((RequirementNode)node).getRequirementId());
		
	}

	public JsTreeNodeDTO getResult() {
		return result;
	}

	public Collection<Project> getProjects() {
		return projects;
	}

	public void setProjects(Collection<Project> projects) {
		this.projects = projects;
	}

}