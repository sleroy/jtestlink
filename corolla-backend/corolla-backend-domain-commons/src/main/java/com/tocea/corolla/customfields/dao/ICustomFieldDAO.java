/**
 *
 */
package com.tocea.corolla.customfields.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.customfields.domain.CustomField;

/**
 * This interface defines the CRUD to manipulate the custom fields.
 *
 * @author sleroy
 *
 *
 */

public interface ICustomFieldDAO extends CrudRepository<CustomField, Integer> {

}
