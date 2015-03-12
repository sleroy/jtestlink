/**
 *
 */
package com.tocea.corolla.customfields.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.customfields.domain.CustomField;

/**
 * This interface defines the CRUD to manipulate the custom fields.
 *
 * @author sleroy
 *
 *
 */
@RepositoryRestResource(path = "/custom_fields", collectionResourceRel = "custom_fields")
public interface ICustomFieldDAO extends CrudRepository<CustomField, Integer> {

}
