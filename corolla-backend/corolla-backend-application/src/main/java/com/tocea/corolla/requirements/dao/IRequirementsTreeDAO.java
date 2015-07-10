package com.tocea.corolla.requirements.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.requirements.domain.RequirementsTree;

public interface IRequirementsTreeDAO extends MongoRepository<RequirementsTree, String> {

	/**
	 * Retrieves a requirements tree from its branch id
	 * @param branchId
	 * @return
	 */
	public RequirementsTree findByBranchId(String branchId);
	
}
