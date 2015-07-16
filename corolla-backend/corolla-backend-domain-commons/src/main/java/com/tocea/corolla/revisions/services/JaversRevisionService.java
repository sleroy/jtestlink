package com.tocea.corolla.revisions.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.javers.core.Javers;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.core.metamodel.object.CdoSnapshot;
import org.javers.core.metamodel.property.Property;
import org.javers.repository.jql.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.tocea.corolla.revisions.domain.Change;
import com.tocea.corolla.revisions.domain.Commit;
import com.tocea.corolla.revisions.domain.IChange;
import com.tocea.corolla.revisions.domain.ICommit;

@Service
public class JaversRevisionService implements IRevisionService {

	private static final Logger	LOGGER = LoggerFactory.getLogger(JaversRevisionService.class);
	
	private static String DEFAULT_USER = "unknown";
	
	@Autowired
	private Javers javers;
	
	/**
	 * Retrieves the name of the current user
	 * @return
	 */
	private String getUsername() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
			
			User user = (User) auth.getPrincipal();
			
			if (user != null) {
				
				return user.getUsername();
			}
			
		}
		
		return DEFAULT_USER;	
	}
	
	@Override
	public void commit(Object obj) {
		
		String username = getUsername();
		LOGGER.info("new commit transaction for user "+username);
		
		javers.commit(username, obj);
	}
	
	@Override
	public Collection<ICommit> getHistory(final String id, final Class<?> clazz) {
		
		List<CdoSnapshot> snapshots = javers.findSnapshots(
				QueryBuilder.byInstanceId(id, clazz).build()
		);
				
		if (snapshots == null) {
			return Lists.newArrayList();
		}
		
		return Lists.transform(snapshots, new Function<CdoSnapshot, ICommit>() {
			@Override
			public ICommit apply(CdoSnapshot snapshot) {				
				return new Commit(id, clazz, snapshot);
			}			
		});

	}
	
	@Override
	public List<IChange> compare(Object oldVersion, Object currentVersion) {
		
		Diff diff = javers.compare(oldVersion, currentVersion);

		List<ValueChange> changes = diff.getChangesByType(ValueChange.class);
		
		if (changes == null) {
			return Lists.newArrayList();
		}
		
		return Lists.transform(changes, new Function<ValueChange, IChange>() {

			@Override
			public IChange apply(ValueChange valueChange) {
				return new Change(valueChange);
			}
			
		});	
		
	}

	@Override
	public Object getSnapshot(ICommit commit) throws Exception {
				
		CdoSnapshot cdoSnapshot = findCdoSnapshotByCommit((Commit)commit);
		
		Class<?> clazz = ((Commit) commit).getObjectClass();
		
		Object object = clazz.newInstance();
		
		if (cdoSnapshot != null) {
			
			for(Property prop : cdoSnapshot.getProperties()) {
				
				PropertyUtils.setProperty(object, prop.getName(), cdoSnapshot.getPropertyValue(prop.getName()));
				
			}
			
		}
		
		return object;
		
	}
	
	private CdoSnapshot findCdoSnapshotByCommit(Commit commit) {
		
		String id = commit.getObjectID();
		Class<?> clazz = commit.getObjectClass();
		
		List<CdoSnapshot> snapshots = javers.findSnapshots(
				QueryBuilder.byInstanceId(id, clazz).build()
		);
		
		if (snapshots != null) {		
			for(CdoSnapshot snapshot : snapshots) {
				if (snapshot.getCommitId().value().equals(commit.getId())) {
					return snapshot;
				}
			}		
		}
		
		return null;
		
	}
	

}