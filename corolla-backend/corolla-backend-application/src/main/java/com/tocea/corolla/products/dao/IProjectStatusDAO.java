package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.ProjectStatus;

public interface IProjectStatusDAO extends MongoRepository<ProjectStatus, String> {

	/**
	 * Retrieve a ProjectStatus instance by its name
	 * @param name
	 * @return
	 */
	public ProjectStatus findByName(String name);
	
}
