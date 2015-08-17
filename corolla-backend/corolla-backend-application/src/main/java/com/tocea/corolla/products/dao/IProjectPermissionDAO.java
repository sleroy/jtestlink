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
 * Corolla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.products.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.products.domain.ProjectPermission;

public interface IProjectPermissionDAO extends MongoRepository<ProjectPermission, String> {

	/**
	 * Retrieve all the permission defined
	 * for a specific project
	 * @param projectId
	 * @return
	 */
	List<ProjectPermission> findByProjectId(String projectId);
	
	/**
	 * Retrieve the permission of a specific entity
	 * @param entityId
	 * @param entityType
	 * @return
	 */
	ProjectPermission findByProjectIdAndEntityIdAndEntityType(String projectId, String entityId, ProjectPermission.EntityType entityType);
	
	/**
	 * Retrieve the permissions of a specific entity
	 * through all the projects
	 * @param entityId
	 * @param entityType
	 * @return
	 */
	List<ProjectPermission> findByEntityIdAndEntityType(String entityId, ProjectPermission.EntityType entityType);
	
}