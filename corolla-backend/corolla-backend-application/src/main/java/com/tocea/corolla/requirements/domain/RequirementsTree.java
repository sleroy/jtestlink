package com.tocea.corolla.requirements.domain;

import java.util.Collection;

import javax.persistence.Embedded;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class RequirementsTree {

	@Id
	@Field("_id")
	private String id;
	
	@NotEmpty
	private String branchId;
	
	@Embedded
	private Collection<TreeNode> nodes;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Collection<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<TreeNode> nodes) {
		this.nodes = nodes;
	}
	
}
