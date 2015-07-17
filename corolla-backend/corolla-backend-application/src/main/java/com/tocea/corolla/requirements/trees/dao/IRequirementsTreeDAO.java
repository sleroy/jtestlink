package com.tocea.corolla.requirements.trees.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.requirements.trees.domain.RequirementsTree;

public interface IRequirementsTreeDAO extends MongoRepository<RequirementsTree, String> {

	/**
	 * Retrieves a requirements tree from its branch id
	 * @param branchId
	 * @return
	 */
	public RequirementsTree findByBranchId(String branchId);
	
	/**
	 * Deletes a requirements tree by its branch id
	 * @param branchId
	 * @return
	 */
	public RequirementsTree deleteByBranchId(String branchId);
	
}
