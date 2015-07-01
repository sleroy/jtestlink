package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.ProjectBranch;

public interface IProjectBranchDAO extends MongoRepository<ProjectBranch, String> {

}
