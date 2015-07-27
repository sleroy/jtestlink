package com.tocea.corolla.requirements.domain;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class Requirement implements Serializable {

	@Id
	@Field("_id")
	private String id;
	
	@NotEmpty
	private String key;
	
	@NotEmpty
	private String projectBranchId;
	
	@NotEmpty
	private String name;
	
	private String description;
	
	private String typeID;
	
	private String statusID;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String type) {
		this.typeID = type;
	}

	public String getProjectBranchId() {
		return projectBranchId;
	}

	public void setProjectBranchId(String projectBranchId) {
		this.projectBranchId = projectBranchId;
	}

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}
	
}