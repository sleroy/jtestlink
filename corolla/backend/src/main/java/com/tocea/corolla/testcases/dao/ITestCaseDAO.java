/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.testcases.domain.TestCase;


public interface ITestCaseDAO extends
CrudRepository<TestCase, Integer> {

}
