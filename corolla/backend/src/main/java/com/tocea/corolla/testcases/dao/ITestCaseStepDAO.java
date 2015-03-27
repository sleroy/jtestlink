/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.testcases.domain.TestCaseStep;


public interface ITestCaseStepDAO extends
CrudRepository<TestCaseStep, Integer> {

}
