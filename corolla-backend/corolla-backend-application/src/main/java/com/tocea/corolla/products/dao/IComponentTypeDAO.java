/**
 *
 */
package com.tocea.corolla.products.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.products.domain.Application;
import com.tocea.corolla.products.domain.ComponentType;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */
public interface IComponentTypeDAO extends CrudRepository<ComponentType, Integer> {

}
