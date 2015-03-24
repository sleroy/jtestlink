/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.testcases.domain.TestCaseRevisionArtifact;

@RepositoryRestResource(path = "/testcase_artifacts", collectionResourceRel = "testcase_artifacts")
public interface ITestCaseRevisionArtifactDAO extends
CrudRepository<TestCaseRevisionArtifact, Integer> {

}
