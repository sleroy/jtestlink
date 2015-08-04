package com.tocea.corolla.portfolio.dao;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tocea.corolla.portfolio.domain.Portfolio;

public interface IPortfolioDAO extends MongoRepository<Portfolio, String> {

	@Query(value="{}")
	public Portfolio find();
	
}
