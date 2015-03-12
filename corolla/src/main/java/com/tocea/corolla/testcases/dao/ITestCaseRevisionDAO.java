/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.testcases.domain.TestCaseRevision;

@RepositoryRestResource(path = "/testcase_revisions", collectionResourceRel = "testcase_revisions")
public interface ITestCaseRevisionDAO extends
CrudRepository<TestCaseRevision, Integer> {

}
