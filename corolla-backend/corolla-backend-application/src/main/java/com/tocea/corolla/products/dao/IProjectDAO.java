package com.tocea.corolla.products.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.Project;

public interface IProjectDAO extends MongoRepository<Project, String> {

	/**
	 * Retrieve a project by its key
	 * @param key
	 * @return
	 */
	public Project findByKey(String key);
	
	/**
	 * Search by tag
	 * @param tags
	 * @return
	 */
	public List<Project> findByTagsIgnoreCase(String tags);
	
}
