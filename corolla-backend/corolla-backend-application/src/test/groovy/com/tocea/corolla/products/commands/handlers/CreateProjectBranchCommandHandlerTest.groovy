package com.tocea.corolla.products.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.products.commands.CreateProjectBranchCommand
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.InvalidProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.ProjectBranchAlreadyExistException;
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.RequirementsTree;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_PROJECT_BRANCH")
class CreateProjectBranchCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IProjectBranchDAO branchDAO = Mock(IProjectBranchDAO)	
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)
	def CreateProjectBranchCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	
	def setup() {
		handler = new CreateProjectBranchCommandHandler(
				branchDAO : branchDAO,
				requirementsTreeDAO : requirementsTreeDAO
		)
	}
	
	def "it should create a new project branch"() {
		
		given:
			def branch = new ProjectBranch()
			branch.name = "Master"
			branch.projectId = "35"
		
		when:
			handler.handle new CreateProjectBranchCommand(branch)
	
		then:
			branchDAO.findByNameAndProjectId(branch.name, branch.projectId) >> null
			notThrown(Exception.class)
			1 * branchDAO.save(branch)		
	}
	
	def "it should create a tree of requirements when creating a new branch"() {
		
		given:
			def branch = new ProjectBranch()
			branch.name = "Master"
			branch.projectId = "35"
		
		when:
			handler.handle new CreateProjectBranchCommand(branch)
	
		then:
			branchDAO.findByNameAndProjectId(branch.name, branch.projectId) >> null
			notThrown(Exception.class)
			1 * requirementsTreeDAO.save({ it != null && it instanceof RequirementsTree })
		
	}
	
	def "it should not create two branches with the same name on the same project"() {
		
		given:
			def branch = new ProjectBranch()
			branch.name = "Master"
			branch.projectId = "35"
			def sameBranch = new ProjectBranch(name: branch.name, projectId: branch.projectId)
		
		when:
			handler.handle new CreateProjectBranchCommand(branch)
	
		then:
			branchDAO.findByNameAndProjectId(branch.name, branch.projectId) >> sameBranch
			0 * branchDAO.save(_)
			thrown(ProjectBranchAlreadyExistException.class)
		
	}
	
	def "it should throw an exception if the branch is null"() {
		
		given:
			def branch = null
		
		when:
			handler.handle new CreateProjectBranchCommand(branch)
	
		then:
			0 * branchDAO.save(_)
			thrown(MissingProjectBranchInformationException.class)
			
	}
	
	def "it should throw an exception if the ID is already defined"() {
		
		given:
			def branch = new ProjectBranch()
			branch.id = "1"
			branch.name = "Master"
			branch.projectId = "35"
		
		when:
			handler.handle new CreateProjectBranchCommand(branch)
	
		then:
			0 * branchDAO.save(_)
			thrown(InvalidProjectBranchInformationException.class)
			
	}
	
}