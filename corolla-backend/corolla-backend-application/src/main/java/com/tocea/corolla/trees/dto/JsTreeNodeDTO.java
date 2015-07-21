package com.tocea.corolla.trees.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tocea.corolla.portfolio.domain.ProjectNode;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.trees.domain.TextNode;
import com.tocea.corolla.trees.domain.TreeNode;

public class JsTreeNodeDTO {

	private String text = "";
	
	private String icon = "";
	
	private Collection<JsTreeNodeDTO> children;
	
	private Map<String, String> a_attr;
	
	public JsTreeNodeDTO() {
		
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Collection<JsTreeNodeDTO> getChildren() {
		return children;
	}

	public void setChildren(Collection<JsTreeNodeDTO> children) {
		this.children = children;
	}

	public Map<String, String> getA_attr() {
		return a_attr;
	}

	public void setA_attr(Map<String, String> a_attr) {
		this.a_attr = a_attr;
	}
	
}
