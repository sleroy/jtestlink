package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.ProjectBranch;

public interface IProjectBranchDAO extends MongoRepository<ProjectBranch, String> {

	/**
	 * Retrieves a ProjectBranch instance from its name and its project id
	 * @param name
	 * @return
	 */
	public ProjectBranch findByNameAndProjectId(String name, String projectId);
	
}
