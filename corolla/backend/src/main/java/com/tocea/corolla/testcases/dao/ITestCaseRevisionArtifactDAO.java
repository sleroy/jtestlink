/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.testcases.domain.TestCaseRevisionArtifact;


public interface ITestCaseRevisionArtifactDAO extends
CrudRepository<TestCaseRevisionArtifact, Integer> {

}
