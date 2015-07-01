package com.tocea.corolla.products.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.ProjectCategory;

public interface IProjectCategoryDAO extends MongoRepository<ProjectCategory, String> {

}
