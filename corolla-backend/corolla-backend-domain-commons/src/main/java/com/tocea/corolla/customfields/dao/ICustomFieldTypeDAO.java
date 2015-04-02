/**
 *
 */
package com.tocea.corolla.customfields.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.customfields.domain.CustomFieldType;


public interface ICustomFieldTypeDAO extends
CrudRepository<CustomFieldType, Integer> {

}
