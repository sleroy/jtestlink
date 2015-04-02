/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.testcases.domain.TestDataSet;


public interface ITestDataSetDAO extends
CrudRepository<TestDataSet, Integer> {

}
