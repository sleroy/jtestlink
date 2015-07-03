package com.tocea.corolla.users.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.users.domain.UserGroup;

public interface IUserGroupDAO extends MongoRepository<UserGroup, String> {

	/*
	 * Find a user group by its name
	 * @param name
	 * @return UserGroup
	 */
	public UserGroup findByName(String name);
	
}
