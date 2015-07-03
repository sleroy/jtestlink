/**
 *
 */
package com.tocea.corolla.products.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.products.domain.Application;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */

public interface IApplicationDAO extends CrudRepository<Application, Integer> {

	Application findApplicationByKey(String key);
	
}
