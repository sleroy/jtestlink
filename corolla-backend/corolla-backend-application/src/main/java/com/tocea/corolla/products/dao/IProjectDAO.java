package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.Project;

public interface IProjectDAO extends MongoRepository<Project, String> {

	/**
	 * Retrieve a project by its key
	 * @param key
	 * @return
	 */
	public Project findByKey(String key);
	
}
