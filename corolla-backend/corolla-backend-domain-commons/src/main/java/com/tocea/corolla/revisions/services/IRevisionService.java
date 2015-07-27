package com.tocea.corolla.revisions.services;

import java.util.Collection;
import java.util.List;

import com.tocea.corolla.revisions.domain.IChange;
import com.tocea.corolla.revisions.domain.ICommit;


public interface IRevisionService {

	/**
	 * Creates a new version of an object
	 * @param obj
	 */
	public void commit(Object obj);
	
	/**
	 * Retrieves the history of an object
	 * @param id
	 * @param clazz
	 * @return
	 */
	public Collection<ICommit> getHistory(String id, Class<?> clazz);
	
	/**
	 * Compares to objects and retrieves the list of changes
	 * @param oldVersion
	 * @param currentVersion
	 * @return
	 */
	public List<IChange> compare(Object oldVersion, Object currentVersion);
	
	/**
	 * Retrieve the state of an object at a given commit
	 * @param commit
	 * @return
	 */
	public Object getSnapshot(ICommit commit);
	
	/**
	 * Retrieve the state of an object at a given commit
	 * @param objectID
	 * @param objectClass
	 * @param commitID
	 * @return
	 */
	public Object getSnapshot(String objectID, Class<?> objectClass, String commitID);
	
	/**
	 * Retrieves the commit that was made before a specific commit
	 * @param commit
	 * @return
	 */
	public ICommit getPreviousCommit(String objectID, Class<?> objectClass, String commitID);
	
	/**
	 * Retrieves a commit from its ID
	 * @param objectID
	 * @param objectClass
	 * @param commitID
	 * @return
	 */
	public ICommit findCommitByID(String objectID, Class<?> objectClass, String commitID);
	
}
