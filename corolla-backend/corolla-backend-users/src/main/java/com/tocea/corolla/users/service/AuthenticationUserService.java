/*
 * Corolla - A Tool to manage software requirements and test cases 
 * Copyright (C) 2015 Tocea
 * 
 * This file is part of Corolla.
 * 
 * Corolla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, 
 * or any later version.
 * 
 * Corolla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.users.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.User;

@Service
public class AuthenticationUserService {

	@Autowired
	private IUserDAO userDAO;
	
	/**
	 * Retrieve the login of the current user
	 * @return
	 */
	public String getAuthenticatedLogin() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null && auth.getPrincipal() != null) {						
			return ((org.springframework.security.core.userdetails.User)auth.getPrincipal()).getUsername();			
		}
		
		return null;
	}
	
	/**
	 * Retrieve the current user
	 * @return
	 */
	public User getAuthenticatedUser() {
		
		String userLogin = getAuthenticatedLogin();
		
		if (StringUtils.isEmpty(userLogin)) {
			return null;
		}
		
		return userDAO.findUserByLogin(userLogin);
		
	}
	
}
