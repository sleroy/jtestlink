package com.tocea.corolla.portfolio.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ProjectNodeDTO {

	@NotEmpty
	@Size(min=3, max=50)
	private String key;
	
	@NotEmpty
	@Size(min=3, max=50)
	private String name;
	
	private Integer parentID;
	
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
	
	public Integer getParentID() {
		return parentID;
	}
	
	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}
	
}