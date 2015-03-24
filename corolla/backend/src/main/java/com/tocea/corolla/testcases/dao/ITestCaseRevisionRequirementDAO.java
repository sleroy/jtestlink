/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.testcases.domain.TestCaseRevisionRequirement;

@RepositoryRestResource(path = "/testcase_requirements", collectionResourceRel = "testcase_requirements")
public interface ITestCaseRevisionRequirementDAO extends
CrudRepository<TestCaseRevisionRequirement, Integer> {

}
