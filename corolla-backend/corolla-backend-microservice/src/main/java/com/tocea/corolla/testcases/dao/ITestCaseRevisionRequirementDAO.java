/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.testcases.domain.TestCaseRevisionRequirement;


public interface ITestCaseRevisionRequirementDAO extends
CrudRepository<TestCaseRevisionRequirement, Integer> {

}
