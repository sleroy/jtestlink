package com.tocea.corolla.trees.dto;

import java.util.Collection;
import java.util.Map;

import com.tocea.corolla.trees.dto.JsTreeNodeDTO;

public class JsTreeNodeDTO {

	private String text = "";
	
	private String icon = "";
	
	private String type;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}