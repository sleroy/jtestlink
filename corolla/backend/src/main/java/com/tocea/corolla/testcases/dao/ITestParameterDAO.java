/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.testcases.domain.TestParameter;

@RepositoryRestResource(path = "/test_parameters", collectionResourceRel = "test_parameters")
public interface ITestParameterDAO extends CrudRepository<TestParameter, Integer> {

}
