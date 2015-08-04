package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.ProjectCategory;

public interface IProjectCategoryDAO extends MongoRepository<ProjectCategory, String> {

	/**
	 * Retrieves a ProjectCategory from its name
	 * @param name
	 * @return
	 */
	public ProjectCategory findByName(String name);
	
}
