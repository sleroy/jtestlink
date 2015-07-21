package com.tocea.corolla.portfolio.exceptions;

import com.tocea.corolla.utils.domain.CorollaDomainException;

public class PortfolioNotFoundException extends CorollaDomainException {

	private static final String MESSAGE = "Portfolio not found";
	
	public PortfolioNotFoundException() {
		super(MESSAGE);
	}
	
}
