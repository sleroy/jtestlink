package com.tocea.corolla.products.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class ProjectBranch implements Serializable {

	@Id
	@Field("_id")
	private String id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String projectId;
	
	private Boolean defaultBranch = false;
	
	public ProjectBranch() {
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Boolean getDefaultBranch() {
		return defaultBranch;
	}

	public void setDefaultBranch(Boolean defaultBranch) {
		this.defaultBranch = defaultBranch;
	}

}
