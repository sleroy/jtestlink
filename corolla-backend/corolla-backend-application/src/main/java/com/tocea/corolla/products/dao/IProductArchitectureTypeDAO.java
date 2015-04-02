/**
 *
 */
package com.tocea.corolla.products.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.products.domain.ProductComponentType;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */
public interface IProductArchitectureTypeDAO extends CrudRepository<ProductComponentType, Integer> {

}
