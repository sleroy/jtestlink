package com.tocea.corolla.revisions.services;

import org.javers.core.Javers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JaversRevisionService implements IRevisionService {

	@Autowired
	private Javers javers;
	
	@Override
	public void commit(Object obj) {
		
		javers.commit("unknown", obj);
	}

}
