package com.tocea.corolla.users.permissions;

import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.products.domain.ProjectPermission
import com.tocea.corolla.requirements.domain.Requirement
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.dao.IUserGroupDAO
import com.tocea.corolla.users.domain.Role
import com.tocea.corolla.users.domain.User
import com.tocea.corolla.users.domain.UserGroup

import spock.lang.Specification;

public class UserPermissionsFactoryTest extends Specification {

	def IRoleDAO roleDAO = Mock(IRoleDAO)
	def IUserGroupDAO groupDAO = Mock(IUserGroupDAO)
	def IProjectPermissionDAO projectPermissionDAO = Mock(IProjectPermissionDAO)
	def IUserPermissionsFactory factory;
	
	def setup() {
		factory = new UserPermissionsFactory(
				roleDAO : roleDAO,
				groupDAO : groupDAO,
				projectPermissionDAO : projectPermissionDAO
		)
	}
	
	def "it should return all user permissions"() {
		
		given:
			def permissions = ["Permission1", "Permission2"]
			def role = new Role(id: "1", permissions: permissions.join(", "))
			def user = new User(id: "1", roleId: role.id)
	
	
		when:
			def result = factory.getUserPermissions(user)
	
		then:
			roleDAO.findOne(role.id) >> role
		
		then:
			result != null
			result.size() == 2
			result.containsAll permissions
			
	}
	
	def "it should return an empty list if the user is null"() {
		
		given:
			def user = null
			
		when:
			def result = factory.getUserPermissions(user)
			
		then:
			result != null
			result.size() == 0
		
	}
	
	def "it should return all the permissions of an user on a given project"() {
		
		given:
			def permissions = ["Permission1", "Permission2"]
			def projectRole = new Role(id: "2", permissions: permissions.join(', '))
			def role = new Role(id: "1", permissions: "Permission0")
			def user = new User(id: "1", roleId: role.id)
			def project = new Project(id: "1")
			def projectPermissions = [
 			      new ProjectPermission(
 			    		  projectId: project.id, 
 			    		  entityId: user.id,
 			    		  entityType: ProjectPermission.EntityType.USER,
 			    		  roleIds: [projectRole.id]
 			      ),
 			      new ProjectPermission(
 			    		  projectId: project.id, 
 			    		  entityId: "2",
 			    		  entityType: ProjectPermission.EntityType.GROUP,
 			    		  roleIds: [role.id]
 			      ),
 			      new ProjectPermission(
 			    		  projectId: project.id, 
 			    		  entityId: "1",
 			    		  entityType: ProjectPermission.EntityType.USER,
 			    		  roleIds: [role.id]
 			      )
 			]
			
		when:
			def result = factory.getUserProjectPermissions(user, project)
		
		then:
			groupDAO.findByUserId(user.login) >> []
			projectPermissionDAO.findByProjectId(project.id) >> projectPermissions
			roleDAO.findOne(projectRole.id) >> projectRole
			
		then:
			result != null
			result.size() == 2
			result.containsAll permissions
		
	}
	
	def "it should return all the permissions of an user on a given project from the groups he belongs to"() {
		
		given:
			def permissions = ["Permission1", "Permission2"]
			def projectRole1 = new Role(id: "2", permissions: permissions[0])
			def projectRole2 = new Role(id: "3", permissions: permissions[1])
			def role = new Role(id: "1", permissions: "Permission0")
			def user = new User(id: "1", login: "user1", roleId: role.id)
			def group1 = new UserGroup(id: "1", userIds: [user.login])
			def group2 = new UserGroup(id: "2", userIds: [user.login])
			def project = new Project(id: "1")
			def projectPermissions = [
 			      new ProjectPermission(
 			    		  projectId: project.id, 
 			    		  entityId: group1.id,
 			    		  entityType: ProjectPermission.EntityType.GROUP,
 			    		  roleIds: [projectRole1.id]
 			      ),
 			      new ProjectPermission(
 			    		  projectId: project.id, 
 			    		  entityId: group2.id,
 			    		  entityType: ProjectPermission.EntityType.GROUP,
 			    		  roleIds: [projectRole2.id]
 			      )
 			]
			
		when:
			def result = factory.getUserProjectPermissions(user, project)
		
		then:
			groupDAO.findByUserId(user.login) >> [group1, group2]
			projectPermissionDAO.findByProjectId(project.id) >> projectPermissions
			roleDAO.findOne(projectRole1.id) >> projectRole1
			roleDAO.findOne(projectRole2.id) >> projectRole2
			
		then:
			result != null
			result.size() == 2
			result.containsAll permissions
		
	}
	
	def "it should not return twice the same project permission"() {
		
		given:	
			def permissions = ["Permission1", "Permission2"]
			def projectRole = new Role(id: "2", permissions: permissions.join(", "))
			def role = new Role(id: "1", permissions: "Permission0")
			def user = new User(id: "1", login: "user1", roleId: role.id)
			def group = new UserGroup(id: "1", userIds: [user.login])
			def project = new Project(id: "1")
			def projectPermissions = [
 			      new ProjectPermission(
 			    		  projectId: project.id, 
 			    		  entityId: user.id,
 			    		  entityType: ProjectPermission.EntityType.USER,
 			    		  roleIds: [projectRole.id]
 			      ),
 			      new ProjectPermission(
 			    		  projectId: project.id, 
 			    		  entityId: group.id,
 			    		  entityType: ProjectPermission.EntityType.GROUP,
 			    		  roleIds: [projectRole.id]
 			      )
 			]
 					
		when:
			def result = factory.getUserProjectPermissions(user, project)
		
		then:
			groupDAO.findByUserId(user.login) >> [group]
			projectPermissionDAO.findByProjectId(project.id) >> projectPermissions
			roleDAO.findOne(projectRole.id) >> projectRole
			
		then:
			result != null
			result.size() == 2
			result.containsAll permissions
		
	}
	
	def "it should return an empty list of permission if the given project is null"() {
		
		given:
			def role = new Role(id: "1", permissions: "Permission0")
			def user = new User(id: "1", login: "user1", roleId: role.id)
			def project = null
			
		when:
			def result = factory.getUserProjectPermissions(user, project)
			
		then:
			result != null
			result.size() == 0
		
	}
	
}
