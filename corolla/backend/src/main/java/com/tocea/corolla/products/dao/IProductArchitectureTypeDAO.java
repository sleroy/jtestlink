/**
 *
 */
package com.tocea.corolla.products.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.products.domain.Product;
import com.tocea.corolla.products.domain.ProductComponentType;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */
@RepositoryRestResource(path = "/architecture_types", collectionResourceRel = "architecture_types")
public interface IProductArchitectureTypeDAO extends CrudRepository<ProductComponentType, Integer> {

}
