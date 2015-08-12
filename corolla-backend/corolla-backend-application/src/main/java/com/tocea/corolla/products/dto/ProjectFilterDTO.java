package com.tocea.corolla.products.dto;

import java.util.List;

public class ProjectFilterDTO {

	private List<String> categoryIds;
	
	private List<String> statusIds;
	
	private List<String> ownerIds;
	
	private List<String> tags;
	
	public ProjectFilterDTO() {
		
	}

	public List<String> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<String> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public List<String> getStatusIds() {
		return statusIds;
	}

	public void setStatusIds(List<String> statusIds) {
		this.statusIds = statusIds;
	}

	public List<String> getOwnerIds() {
		return ownerIds;
	}

	public void setOwnerIds(List<String> ownerIds) {
		this.ownerIds = ownerIds;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}