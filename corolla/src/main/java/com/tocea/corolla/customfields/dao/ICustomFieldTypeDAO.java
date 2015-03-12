/**
 *
 */
package com.tocea.corolla.customfields.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.customfields.domain.CustomFieldType;

@RepositoryRestResource(path = "/custom_field_types", collectionResourceRel = "custom_field_types")
public interface ICustomFieldTypeDAO extends
CrudRepository<CustomFieldType, Integer> {

}
