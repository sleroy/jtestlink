/**
 *
 */
package com.tocea.corolla.products.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.products.domain.Component;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */

public interface IComponentDAO extends CrudRepository<Component, Integer> {

}
