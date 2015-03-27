/**
 *
 */
package com.tocea.corolla.testcases.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.testcases.domain.TestParameter;


public interface ITestParameterDAO extends CrudRepository<TestParameter, Integer> {

}
