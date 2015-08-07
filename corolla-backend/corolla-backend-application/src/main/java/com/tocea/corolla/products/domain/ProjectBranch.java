package com.tocea.corolla.products.domain;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.tocea.corolla.utils.domain.ObjectValidation;

@Document
public class ProjectBranch implements Serializable {

	@Id
	@Field("_id")
	private String id;
	
	@NotEmpty
	@Size(min=3, max=50)
	@Pattern(regexp=ObjectValidation.URL_SAFE_PATTERN)
	private String name;
	
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
