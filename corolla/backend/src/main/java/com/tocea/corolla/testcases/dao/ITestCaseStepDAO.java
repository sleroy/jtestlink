/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.testcases.domain.TestCaseStep;

@RepositoryRestResource(path = "/testcase_steps", collectionResourceRel = "testcase_steps")
public interface ITestCaseStepDAO extends
CrudRepository<TestCaseStep, Integer> {

}
