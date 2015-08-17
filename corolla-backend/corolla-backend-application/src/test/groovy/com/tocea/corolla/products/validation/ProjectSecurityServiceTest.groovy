package com.tocea.corolla.products.validation;

import java.util.Collection;

import org.junit.Rule;

import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.products.domain.ProjectPermission
import com.tocea.corolla.products.utils.ProjectUtils;
import com.tocea.corolla.users.dao.IRoleDAO;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.users.domain.UserGroup
import com.tocea.corolla.users.service.AuthenticationUserService;

import spock.lang.Specification;


public class ProjectSecurityServiceTest extends Specification {
	
	def IProjectDAO projectDAO = Mock(IProjectDAO)
	def IRoleDAO roleDAO = Mock(IRoleDAO)
	def IUserGroupDAO groupDAO = Mock(IUserGroupDAO)
	def IProjectPermissionDAO projectPermissionDAO = Mock(IProjectPermissionDAO)
	def AuthenticationUserService authService = Mock(AuthenticationUserService)
	def ProjectSecurityService securityService
	
	def setup() {
		securityService = new ProjectSecurityService(
				projectDAO				: projectDAO,
				roleDAO					: roleDAO,
				groupDAO				: groupDAO,
				projectPermissionDAO 	: projectPermissionDAO,
				authService				: authService
		)
	}
	
	def "it should not grant access to anonymous user"() {
		
		given:
			def right = "MY_PERMISSION"
			def project = new Project(id: "1", key: "P1")
	
		when:
			def ok = securityService.hasPermission project.key, right
			
		then:
			authService.getAuthenticatedUser() >> null
		
		then:
			!ok
			notThrown(Exception.class)
		
	}
	
	def "it should grant access to a non-existing project"() {
		
		given:
			def right = "MY_PERMISSION"
			def project = new Project(id: "1", key: "P1")
			def user = new User(id: "1", login: "jsnow")
	
		when:
			def ok = securityService.hasPermission project.key, right
			
		then:
			authService.getAuthenticatedUser() >> user
			projectDAO.findByKey(project.key) >> null
		
		then:
			ok
			notThrown(Exception.class)
		
	}
	
	def "it should grant access if there is a permission defined for the current user"() {
		
		given:
			def right = "MY_PERMISSION"
			def project = new Project(id: "1", key: "P1")
			def user = new User(id: "1", login: "jsnow")
			def roleWithMatchingRight = new Role(id: "1", permissions: right)
			def projectPermissions = [
			      new ProjectPermission(
			    		  projectId: project.id, 
			    		  entityId: user.id,
			    		  entityType: ProjectPermission.EntityType.USER,
			    		  roleIds: [roleWithMatchingRight.id]
			      )
			]		
	
		when:
			def ok = securityService.hasPermission project.key, right
			
		then:
			authService.getAuthenticatedUser() >> user
			projectDAO.findByKey(project.key) >> project
			groupDAO.findByUserId(_) >> []
			projectPermissionDAO.findByProjectId(project.id) >> projectPermissions
			roleDAO.findOne(roleWithMatchingRight.id) >> roleWithMatchingRight
		
		then:
			ok
			notThrown(Exception.class)
		
	}
	
	def "it should grant access if there is the current user is part of a group that has permission on this project"() {
		
		given:
			def right = "MY_PERMISSION"
			def project = new Project(id: "1", key: "P1")
			def user = new User(id: "1", login: "jsnow")
			def group = new UserGroup(id: "1", userIds: [user.id])
			def roleWithMatchingRight = new Role(id: "1", permissions: right)
			def projectPermissions = [
			      new ProjectPermission(
			    		  projectId: project.id, 
			    		  entityId: group.id,
			    		  entityType: ProjectPermission.EntityType.GROUP,
			    		  roleIds: [roleWithMatchingRight.id]
			      )
			]		
	
		when:
			def ok = securityService.hasPermission project.key, right
			
		then:
			authService.getAuthenticatedUser() >> user
			projectDAO.findByKey(project.key) >> project
			groupDAO.findByUserId(_) >> [group]
			projectPermissionDAO.findByProjectId(project.id) >> projectPermissions
			roleDAO.findOne(roleWithMatchingRight.id) >> roleWithMatchingRight
		
		then:
			ok
			notThrown(Exception.class)
		
	}
	
	def "it should not access permission if the current user has no permission on this project"() {
		
		given:
			def right = "MY_PERMISSION"
			def project = new Project(id: "1", key: "P1")
			def user = new User(id: "1", login: "jsnow")
			def roleWithMatchingRight = new Role(id: "1", permissions: right)
			def projectPermissions = [
			      new ProjectPermission(
			    		  projectId: project.id, 
			    		  entityId: "2",
			    		  entityType: ProjectPermission.EntityType.USER,
			    		  roleIds: [roleWithMatchingRight.id]
			      )
			]		
	
		when:
			def ok = securityService.hasPermission project.key, right
			
		then:
			authService.getAuthenticatedUser() >> user
			projectDAO.findByKey(project.key) >> project
			groupDAO.findByUserId(_) >> []
			projectPermissionDAO.findByProjectId(project.id) >> projectPermissions
			roleDAO.findOne(roleWithMatchingRight.id) >> roleWithMatchingRight
		
		then:
			!ok
			notThrown(Exception.class)
		
	}
	
	def "it should retrieve the full list of the IDs of the projects on which the current user has access"() {
		
		given:
			def user = new User(id: "1", login: "jsnow")
			def group = new UserGroup(id: "1", userIds: [user.id])
			def permissions = [
			      new ProjectPermission(
			    		  projectId: "1", 
			    		  entityId: "2",
			    		  entityType: ProjectPermission.EntityType.USER,
			    		  roleIds: []
			      ),
			      new ProjectPermission(
			    		  projectId: "2", 
			    		  entityId: user.id,
			    		  entityType: ProjectPermission.EntityType.USER,
			    		  roleIds: []
			      ),
			      new ProjectPermission(
			    		  projectId: "3", 
			    		  entityId: group.id,
			    		  entityType: ProjectPermission.EntityType.GROUP,
			    		  roleIds: []
			      ),
			      new ProjectPermission(
			    		  projectId: "4", 
			    		  entityId: "18",
			    		  entityType: ProjectPermission.EntityType.GROUP,
			    		  roleIds: []
			      )
			]	
					
		when:
			def result = securityService.allowedProjectIds()
			
		then:
			authService.getAuthenticatedUser() >> user
			projectPermissionDAO.findByEntityIdAndEntityType(user.id, ProjectPermission.EntityType.USER) >> [permissions[1]]
			groupDAO.findByUserId(user.id) >> [group]
			projectPermissionDAO.findByEntityIdAndEntityType(group.id, ProjectPermission.EntityType.GROUP) >> [permissions[2]]	
			
		then:
			result != null
			result.size() == 2
			result.containsAll "2", "3"
			notThrown(Exception.class)
		
	}
	
	def "the list of allowed projects should not contain duplicates values"() {
		
		given:
			def user = new User(id: "1", login: "jsnow")
			def group = new UserGroup(id: "1", userIds: [user.id])
			def permissions = [
			      new ProjectPermission(
			    		  projectId: "1", 
			    		  entityId: user.id,
			    		  entityType: ProjectPermission.EntityType.USER,
			    		  roleIds: []
			      ),
			      new ProjectPermission(
			    		  projectId: "1", 
			    		  entityId: group.id,
			    		  entityType: ProjectPermission.EntityType.GROUP,
			    		  roleIds: []
			      )
			]	
					
		when:
			def result = securityService.allowedProjectIds()
			
		then:
			authService.getAuthenticatedUser() >> user
			projectPermissionDAO.findByEntityIdAndEntityType(user.id, ProjectPermission.EntityType.USER) >> [permissions[0]]
			groupDAO.findByUserId(user.id) >> [group]
			projectPermissionDAO.findByEntityIdAndEntityType(group.id, ProjectPermission.EntityType.GROUP) >> [permissions[1]]	
			
		then:
			result != null
			result.size() == 1
			result.contains "1"
		
	}
	
}