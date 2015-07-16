package com.tocea.corolla.requirements.dao;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.requirements.domain.Requirement;

public interface IRequirementDAO extends MongoRepository<Requirement, String> {

	/**
	 * Retrieves a requirement by its key and its project branch id
	 * @param key
	 * @return
	 */
	public Requirement findByKeyAndProjectBranchId(String key, String projectBranchId);
	
	/**
	 * Retrieves all the requirements attached to a project branch
	 * @param projectBranchId
	 * @return
	 */
	public Collection<Requirement> findByProjectBranchId(String projectBranchId);
	
}
