/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.testcases.domain.TestCaseRevision;


public interface ITestCaseRevisionDAO extends
CrudRepository<TestCaseRevision, Integer> {

}
