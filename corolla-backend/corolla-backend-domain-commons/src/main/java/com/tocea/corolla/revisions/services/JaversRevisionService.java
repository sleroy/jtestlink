package com.tocea.corolla.revisions.services;

import org.javers.core.Javers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

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

}
