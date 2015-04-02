/**
 *
 */
package com.tocea.corolla.requirements.dao;

import org.springframework.data.repository.CrudRepository;

import com.tocea.corolla.requirements.domain.RequirementArtifactVersion;


public interface IRequirementArtifactVersionDAO extends
CrudRepository<RequirementArtifactVersion, Integer> {

}
