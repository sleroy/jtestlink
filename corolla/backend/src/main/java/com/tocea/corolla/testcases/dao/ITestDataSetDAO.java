/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.testcases.domain.TestDataSet;

@RepositoryRestResource(path = "/test_datasets", collectionResourceRel = "test_datasets")
public interface ITestDataSetDAO extends
CrudRepository<TestDataSet, Integer> {

}
