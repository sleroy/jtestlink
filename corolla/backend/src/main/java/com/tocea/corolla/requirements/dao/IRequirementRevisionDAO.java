/**
 *
 */
package com.tocea.corolla.requirements.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.requirements.domain.RequirementRevision;

@RepositoryRestResource(path = "/requirement_revisions", collectionResourceRel = "requirement_revisions")
public interface IRequirementRevisionDAO extends
CrudRepository<RequirementRevision, Integer> {

}
