package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.ProjectStatus;

public interface IProjectStatusDAO extends MongoRepository<ProjectStatus, String> {

	
}
