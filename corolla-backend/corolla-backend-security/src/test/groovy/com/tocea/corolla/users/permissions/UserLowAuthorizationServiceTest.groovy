package com.tocea.corolla.users.permissions;

import com.google.common.collect.Lists;
import com.tocea.corolla.products.dao.IProjectDAO
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.users.service.AuthenticationUserService

import spock.lang.Specification;

public class UserLowAuthorizationServiceTest extends Specification {

	def IProjectDAO projectDAO = Mock(IProjectDAO)
	def IUserPermissionsFactory permissionFactory = Mock(IUserPermissionsFactory);
	def AuthenticationUserService authService = Mock(AuthenticationUserService);
	def IUserLowAuthorization service;
	
	def setup() {
		service = new UserLowAuthorizationService(
				projectDAO 			: projectDAO,
				permissionFactory 	: permissionFactory,
				authService 		: authService
		)
	}
	
	def "it should indicate if the current user has a specific permission"() {
		
		given:
			def user = new User(id: "1")
			def permissions = ["Permission1", "Permission2"]
	
		when:
			def ok = service.hasPermission(permissions[1])
	
		then:
			authService.getAuthenticatedUser() >> user
			permissionFactory.getUserPermissions(user) >> permissions
		
		then:
			ok
			
	}
	
	def "it should indicate if the current user has a specific permission on a given project"() {
		
		given:
			def user = new User(id: "1")
			def project = new Project(id: "1", key: "P1")
			def permissions = ["Permission1", "Permission2"]
	
		when:
			def ok = service.hasProjectPermission(project.key, permissions[1])
	
		then:
			authService.getAuthenticatedUser() >> user
			projectDAO.findByKey(project.key) >> project
			permissionFactory.getUserProjectPermissions(user, project) >> permissions
		
		then:
			ok
		
	}
	
	def "it should filter a list of projects by the current user permissions"() {
		
		given:
			def user = new User(id: "1")
			def permissions = ["Permission1", "Permission2"]
			def projects = [
			       new Project(id: "1", key: "P1"), 
			       new Project(id: "2", key: "P2"), 
			       new Project(id: "3", key: "P3"),
			       new Project(id: "4", key: "P4")
			]
			
			
		when:
			def filtered = service.filterAllowedProjects(projects*.key.iterator(), permissions)
			def filteredList = Lists.newArrayList(filtered)
			
		then:
			authService.getAuthenticatedUser() >> user
			projectDAO.findByKey(projects[0].key) >> projects[0]
			projectDAO.findByKey(projects[1].key) >> projects[1]
			projectDAO.findByKey(projects[2].key) >> projects[2]
			projectDAO.findByKey(projects[3].key) >> projects[3]
			permissionFactory.getUserProjectPermissions(user, projects[0]) >> [permissions[1]]
			permissionFactory.getUserProjectPermissions(user, projects[1]) >> permissions
			permissionFactory.getUserProjectPermissions(user, projects[2]) >> permissions	
			permissionFactory.getUserProjectPermissions(user, projects[3]) >> []
					
		then:
			filtered != null
			filteredList.size() == 2
			filteredList.contains projects[1].key
			filteredList.contains projects[2].key
			
	}
	
}
