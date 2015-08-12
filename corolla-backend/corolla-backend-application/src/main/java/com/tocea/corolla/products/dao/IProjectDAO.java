/*
 * Corolla - A Tool to manage software requirements and test cases 
 * Copyright (C) 2015 Tocea
 * 
 * This file is part of Corolla.
 * 
 * Corolla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, 
 * or any later version.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.products.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tocea.corolla.products.domain.Project;

public interface IProjectDAO extends MongoRepository<Project, String> {

	/**
	 * Retrieve a project by its key
	 * @param key
	 * @return
	 */
	Project findByKey(String key);
	
	/**
	 * Search by tag
	 * @param tags
	 * @return
	 */
	List<Project> findByTagsIgnoreCase(String tags);
	
	/**
	 * Search through all projects
	 * by category, status, owner and tags
	 * @param categoryIds
	 * @param statusIds
	 * @param tags
	 * @return
	 */
	@Query("{ 'categoryId' : { $in: ?0 }, 'statusId' : { $in: ?1 }, 'ownerId': { $in: ?2 }, 'tags' : { $in: ?3 } }")
	public List<Project> findProjects(List<String> categoryIds, List<String> statusIds, List<String> ownerId, List<String> tags);
	
}