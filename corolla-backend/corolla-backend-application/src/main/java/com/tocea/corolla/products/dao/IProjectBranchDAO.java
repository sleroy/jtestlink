package com.tocea.corolla.products.dao;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.ProjectBranch;

public interface IProjectBranchDAO extends MongoRepository<ProjectBranch, String> {

	/**
	 * Retrieves a ProjectBranch instance from its name and its project id
	 * @param name
	 * @return
	 */
	public ProjectBranch findByNameAndProjectId(String name, String projectId);
	
	/**
	 * Retrieves all ProjectBranch instances from a project id
	 * @param projectId
	 * @return
	 */
	public Collection<ProjectBranch> findByProjectId(String projectId);
	
}
