/**
 *
 */
package com.tocea.corolla.products.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.products.domain.Product;

/**
 * This interface defines the CRUD to manipulate members.
 *
 * @author sleroy
 *
 *
 */
@RepositoryRestResource(path = "/products", collectionResourceRel = "products")
public interface IProductDAO extends CrudRepository<Product, Integer> {

}
