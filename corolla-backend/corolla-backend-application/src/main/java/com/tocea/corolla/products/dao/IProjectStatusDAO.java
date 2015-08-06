package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tocea.corolla.products.domain.ProjectStatus;

public interface IProjectStatusDAO extends MongoRepository<ProjectStatus, String> {

	/**
	 * Retrieve a ProjectStatus instance by its name
	 * @param name
	 * @return
	 */
	public ProjectStatus findByName(String name);
	
	/**
	 * Returns the default project status
	 * @return the default project status.
	 */
	@Query("{ 'defaultStatus': true }")
	public ProjectStatus getDefaultStatus();
	
}
