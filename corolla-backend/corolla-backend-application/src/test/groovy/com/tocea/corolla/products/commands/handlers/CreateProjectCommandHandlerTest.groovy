package com.tocea.corolla.products.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.products.commands.CreateProjectCommand
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.products.exceptions.InvalidProjectInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectInformationException;
import com.tocea.corolla.products.exceptions.ProjectAlreadyExistException;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_PROJECT")
class CreateProjectCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IProjectDAO projectDAO = Mock(IProjectDAO)	
	def CreateProjectCommandHandler handler
	
	def setup() {
		handler = new CreateProjectCommandHandler(
				projectDAO : projectDAO,
		)
	}
	
	def "it should create a new project"() {
		
		given:
			def project = new Project()
			project.key = "my_project"
			project.name = "My Awesome Project"
		
		when:
			handler.handle new CreateProjectCommand(project)
	
		then:
			projectDAO.findByKey(project.key) >> null
			1 * projectDAO.save(_)
			notThrown(Exception.class)
	}
	
	def "it should create two projects with the same key"() {
		
		given:
			def project = new Project()
			project.key = "my_project"
			project.name = "My Awesome Project"
		
		when:
			handler.handle new CreateProjectCommand(project)
	
		then:
			projectDAO.findByKey(project.key) >> new Project(key: project.key)
			0 * projectDAO.save(_)
			thrown(ProjectAlreadyExistException.class)
		
	}
	
	def "it should throw an exception if the project is null"() {
		
		given:
			def project = null
		
		when:
			handler.handle new CreateProjectCommand(project)
	
		then:
			0 * projectDAO.save(_)
			thrown(MissingProjectInformationException.class)
			
	}
	
	def "it should throw an exception if the ID is already defined"() {
		
		given:
			def project = new Project()
			project.id = "1"
			project.key = "my_project"
			project.name = "My Awesome Project"
		
		when:
			handler.handle new CreateProjectCommand(project)
	
		then:
			0 * projectDAO.save(_)
			thrown(InvalidProjectInformationException.class)
			
	}
	
}