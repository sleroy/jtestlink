package com.tocea.corolla.trees.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class FolderNodeType {

	@Id
	@Field("_id")
	private String id;
	
	@NotBlank
	@Size(min=1, max=100)
	private String name;
	
	private String icon;
	
	public FolderNodeType() {
		super();
	}
	
	public FolderNodeType(String name, String icon) {
		super();
		setName(name);
		setIcon(icon);
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}