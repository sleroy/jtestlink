/**
 *
 */
package com.tocea.corolla.requirements.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.requirements.domain.Requirement;


public interface IRequirementDAO extends
CrudRepository<Requirement, Integer> {

}
