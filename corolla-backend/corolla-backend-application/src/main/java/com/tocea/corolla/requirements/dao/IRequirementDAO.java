package com.tocea.corolla.requirements.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.requirements.domain.Requirement;

public interface IRequirementDAO extends MongoRepository<Requirement, String> {

	/**
	 * Retrieves a requirement by its key and its project branch id
	 * @param key
	 * @return
	 */
	public Requirement findByKeyAndProjectBranchId(String key, String projectBranchId);
	
}
