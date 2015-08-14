package com.tocea.corolla.products.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.products.commands.CreateProjectPermissionCommand
import com.tocea.corolla.products.dao.IProjectPermissionDAO;
import com.tocea.corolla.products.domain.ProjectPermission
import com.tocea.corolla.products.exceptions.*;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_PROJECT_PERMISSION")
class CreateProjectPermissionCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IProjectPermissionDAO permissionDAO = Mock(IProjectPermissionDAO)	
	def CreateProjectPermissionCommandHandler handler
	
	def setup() {
		handler = new CreateProjectPermissionCommandHandler(
				permissionDAO : permissionDAO
		)
	}
	
	def "it should create a new project permisison"() {
		
		given:
			def permission = new ProjectPermission()
			permission.projectId = "P1"
			permission.entityId = "U1"
			permission.entityType = ProjectPermission.EntityType.USER
			permission.roleIds = ["R1", "R2"]
		
		when:
			handler.handle new CreateProjectPermissionCommand(permission)
	
		then:
			1 * permissionDAO.save(permission)
			notThrown(Exception.class)
	}
	
	def "it should not create two project permisisons on the same entity"() {
		
		given:
			def permission = new ProjectPermission()
			permission.projectId = "P1"
			permission.entityId = "U1"
			permission.entityType = ProjectPermission.EntityType.USER
			permission.roleIds = ["R1", "R2"]
			def alreadyExistingPermission = new ProjectPermission(
					projectId: permission.projectId,
					entityId: permission.entityId,
					entityType: permission.entityType
			)
		
		when:
			handler.handle new CreateProjectPermissionCommand(permission)
	
		then:
			permissionDAO.findByProjectIdAndEntityIdAndEntityType(permission.projectId, permission.entityId, permission.entityType) >> alreadyExistingPermission
			
		then:
			0 * permissionDAO.save(permission)
			thrown(ProjectPermissionAlreadyExistException.class)
	}
	
	def "it should throw an exception if the ID is already defined"() {
		
		given:
			def permission = new ProjectPermission()
			permission.id = "1"
			permission.projectId = "P1"
			permission.entityId = "U1"
			permission.entityType = ProjectPermission.EntityType.USER
			permission.roleIds = ["R1", "R2"]
		
		when:
			handler.handle new CreateProjectPermissionCommand(permission)
	
		then:
			0 * permissionDAO.save(permission)
			thrown(InvalidProjectPermissionInformationException.class)
	}
	
}
