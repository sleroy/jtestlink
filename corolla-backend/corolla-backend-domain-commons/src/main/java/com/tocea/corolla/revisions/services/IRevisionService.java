package com.tocea.corolla.revisions.services;


public interface IRevisionService {

	/**
	 * Creates a new version of an object
	 * @param obj
	 */
	public void commit(Object obj);
	
}
