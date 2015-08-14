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
package com.tocea.corolla.users.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tocea.corolla.users.domain.UserGroup;

public interface IUserGroupDAO extends MongoRepository<UserGroup, String> {

	/*
	 * Find a user group by its name
	 * @param name
	 * @return UserGroup
	 */
	UserGroup findByName(String name);
	
	/**
	 * Find all user groups of a user
	 * @param userId
	 * @return
	 */
	@Query("{ 'userIds': ?0 }")
	List<UserGroup> findByUserId(String userId);
	
}
