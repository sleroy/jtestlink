/**
 *
 */
package com.tocea.corolla.products.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.products.domain.ProductArchitecture;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */
@RepositoryRestResource(path = "/product_architectures", collectionResourceRel = "product_architectures")
public interface IProductArchitectureDAO extends CrudRepository<ProductArchitecture, Integer> {

}
