package com.tocea.corolla.products.domain;

import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import javax.persistence.Embedded;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.tocea.corolla.utils.domain.ObjectValidation;

@Document
public class Project implements Serializable {

	@Id
	@Field("_id")
	private String id;
	
	@NotEmpty
	@Size(min=3, max=50)
	@Pattern(regexp=ObjectValidation.URL_SAFE_PATTERN)
	private String key;
	
	@NotEmpty
	@Size(min=3, max=100)
	private String name;
	
	private String statusId;
	
	private String categoryId;
	
	private String ownerId;
	
	private String description;
	
	private URL image;
	
	@Embedded
	private List<String> tags;

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

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public URL getImage() {
		return image;
	}

	public void setImage(URL image) {
		this.image = image;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}