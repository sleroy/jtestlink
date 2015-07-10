package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.RequirementType;

public interface IRequirementTypeDAO extends MongoRepository<RequirementType, String> {

	/**
	 * Retrieves a RequirementType instance by its key
	 * @param key
	 * @return
	 */
	public RequirementType findByKey(String key);
	
}
