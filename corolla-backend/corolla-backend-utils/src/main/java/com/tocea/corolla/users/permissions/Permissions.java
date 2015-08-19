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
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.users.permissions;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

/**
 * Defines permissions employed in authorization mechanism.
 *
 * @author sleroy
 *
 */
@Component
public class Permissions {
	
	public static final String	REST				= "ROLE_REST";

	public static final String	ADMIN				= "ADMIN";
	public static final String	ADMIN_CONFIG		= "ADMIN_CONFIG";
	
	public static final String PORTFOLIO_READ 		= "PORTFOLIO_READ";
	public static final String PORTFOLIO_WRITE 		= "PORTFOLIO_WRITE";
	public static final String PROJECT_CREATE 		= "PROJECT_CREATE";
	public static final String PROJECT_DELETE		= "PROJECT_DELETE";
	
	public static final String PROJECT_READ			= "PROJECT_READ";
	public static final String PROJECT_EDIT			= "PROJECT_EDIT";
	
	public static final String REQUIREMENTS_READ	= "REQUIREMENT_READ";
	public static final String REQUIREMENTS_WRITE	= "REQUIREMENT_WRITE";
	public static final String REQUIREMENTS_REPORT	= "REQUIREMENT_REPORT";
	
	public static final String GROUP_ADMIN			= "ADMIN";
	public static final String GROUP_PORTFOLIO		= "PORTFOLIO";
	public static final String GROUP_PROJECT		= "PROJECT";
	public static final String GROUP_REQUIREMENTS	= "REQUIREMENTS";
	
	private Collection<Permission> permissions;
	
	@PostConstruct
	public void onInit() {
		
		loadStaticPermissions();
	}
	
	/**
	 * Loads static permissions
	 */
	private void loadStaticPermissions() {
		
		permissions = Lists.newArrayList();
		
		permissions.add(new Permission(ADMIN, GROUP_ADMIN));
		permissions.add(new Permission(ADMIN_CONFIG, GROUP_ADMIN));
		
		permissions.add(new Permission(PORTFOLIO_READ, GROUP_PORTFOLIO));
		permissions.add(new Permission(PORTFOLIO_WRITE, GROUP_PORTFOLIO));
		permissions.add(new Permission(PROJECT_CREATE, GROUP_PORTFOLIO));
		permissions.add(new Permission(PROJECT_DELETE, GROUP_PORTFOLIO));
		
		permissions.add(new Permission(PROJECT_READ, GROUP_PROJECT));
		permissions.add(new Permission(PROJECT_EDIT, GROUP_PROJECT));
		
		permissions.add(new Permission(REQUIREMENTS_READ, GROUP_REQUIREMENTS));
		permissions.add(new Permission(REQUIREMENTS_WRITE, GROUP_REQUIREMENTS));
		permissions.add(new Permission(REQUIREMENTS_REPORT, GROUP_REQUIREMENTS));
		
	}
	
	/**
	 * Retrieves the list of all existing permission's keys
	 * @return
	 */
	public List<String> list() {
		
		return Lists.newArrayList(Collections2.transform(permissions, new Function<Permission, String>() {
			@Override
			public String apply(Permission permission) {
				return permission != null ? permission.getKey() : null;
			}			
		}));
		
	}
	
}
