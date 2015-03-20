/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.testcases.domain.TestCase;

@RepositoryRestResource(path = "/testcases", collectionResourceRel = "testcases")
public interface ITestCaseDAO extends
CrudRepository<TestCase, Integer> {

}
