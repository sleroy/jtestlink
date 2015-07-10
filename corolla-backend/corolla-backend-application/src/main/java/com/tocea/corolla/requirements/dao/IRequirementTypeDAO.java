package com.tocea.corolla.requirements.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.requirements.domain.RequirementType;

public interface IRequirementTypeDAO extends MongoRepository<RequirementType, String> {

	/**
	 * Retrieves a RequirementType instance by its key
	 * @param key
	 * @return
	 */
	public RequirementType findByKey(String key);
	
}
