package com.tocea.corolla.portfolio.domain;

import java.util.Collection;

import javax.persistence.Embedded;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.tocea.corolla.trees.domain.ITree;
import com.tocea.corolla.trees.domain.TreeNode;

@Document
public class Portfolio implements ITree {

	@Id
	@Field("_id")
	private String id;
	
	@Embedded
	private Collection<TreeNode> nodes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Collection<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<TreeNode> nodes) {
		this.nodes = nodes;
	}

}
