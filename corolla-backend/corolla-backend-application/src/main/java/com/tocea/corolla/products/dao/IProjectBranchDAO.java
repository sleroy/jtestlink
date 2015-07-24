package com.tocea.corolla.products.dao;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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
	
	/**
	 * Retrieves the default branch of a given project
	 * @param projectId
	 * @return
	 */
	@Query(value="{ 'projectId': ?0, 'defaultBranch': true }")
	public ProjectBranch findDefaultBranch(String projectId);
	
}
