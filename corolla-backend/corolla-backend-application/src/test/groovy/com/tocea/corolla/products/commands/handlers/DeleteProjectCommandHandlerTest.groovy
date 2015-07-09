package com.tocea.corolla.products.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.products.commands.DeleteProjectCommand
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.products.exceptions.*;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_PROJECT")
class DeleteProjectCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IProjectDAO projectDAO = Mock(IProjectDAO)	
	def DeleteProjectCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	
	def setup() {
		handler = new DeleteProjectCommandHandler(
				projectDAO : projectDAO,
				revisionService : revisionService
		)
	}
	
	def "it should delete a project"() {
		
		given:
			def project = new Project()
			project.id = "3"
			project.key = "my_project"
			project.name = "My Awesome Project"
		
		when:
			handler.handle new DeleteProjectCommand(project.id)
	
		then:
			projectDAO.findOne(project.id) >> project
			1 * projectDAO.delete(_)
			notThrown(Exception.class)
	}
	
	def "it should throw an exception if the project ID is empty"() {
		
		given:
			def project = new Project()
			project.id = ""
			project.key = "my_project"
			project.name = "My Awesome Project"
		
		when:
			handler.handle new DeleteProjectCommand(project.id)
	
		then:
			0 * projectDAO.delete(_)
			thrown(MissingProjectInformationException.class)
		
	}
	
	def "it should throw an exception if the project does not exist"() {
		
		given:
			def project = new Project()
			project.id = "3"
			project.key = "my_project"
			project.name = "My Awesome Project"
		
		when:
			handler.handle new DeleteProjectCommand(project.id)
	
		then:
			projectDAO.findOne(project.id) >> null
			0 * projectDAO.delete(_)
			thrown(ProjectNotFoundException.class)
		
	}
	
}