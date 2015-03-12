/**
 *
 */
package com.tocea.corolla.requirements.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.requirements.domain.Requirement;

@RepositoryRestResource(path = "/requirements", collectionResourceRel = "requirements")
public interface IRequirementDAO extends
CrudRepository<Requirement, Integer> {

}
