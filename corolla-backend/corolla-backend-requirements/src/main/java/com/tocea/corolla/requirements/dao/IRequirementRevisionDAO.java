/**
 *
 */
package com.tocea.corolla.requirements.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.requirements.domain.RequirementRevision;


public interface IRequirementRevisionDAO extends
CrudRepository<RequirementRevision, Integer> {

}
