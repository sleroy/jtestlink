/**
 *
 */
package com.tocea.corolla.requirements.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.requirements.domain.RequirementArtifactVersion;

@RepositoryRestResource(path = "/requirement_artifacts", collectionResourceRel = "requirement_artifacts")
public interface IRequirementArtifactVersionDAO extends
CrudRepository<RequirementArtifactVersion, Integer> {

}
