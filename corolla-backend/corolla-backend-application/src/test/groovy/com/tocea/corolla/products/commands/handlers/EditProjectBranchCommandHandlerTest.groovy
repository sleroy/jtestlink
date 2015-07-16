package com.tocea.corolla.products.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.products.commands.EditProjectBranchCommand
import com.tocea.corolla.products.dao.IProjectBranchDAO
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.InvalidProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "EDIT_PROJECT")
class EditProjectBranchCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IProjectBranchDAO branchDAO = Mock(IProjectBranchDAO)	
	def EditProjectBranchCommandHandler handler
	
	def setup() {
		handler = new EditProjectBranchCommandHandler(
				branchDAO : branchDAO
		)
	}
	
	def "it should edit a project"() {
		
		given:
			def branch = new ProjectBranch(id: 1, name: "old")
		
		when:
			handler.handle new EditProjectBranchCommand(branch)
	
		then:
			notThrown(Exception.class)
			1 * branchDAO.save(branch)		
	}

	def "it should throw an exception if the branch is missing"() {
		
		given:
			def branch = null
		
		when:
			handler.handle new EditProjectBranchCommand(branch)
	
		then:
			0 * branchDAO.save(_)
			thrown(MissingProjectBranchInformationException.class)
			
	}
	
	def "it should throw an exception if the ID is not defined"() {
		
		given:
			def branch = new ProjectBranch(id: "", name: "old")
		
		when:
			handler.handle new EditProjectBranchCommand(branch)
	
		then:
			0 * branchDAO.save(_)
			thrown(InvalidProjectBranchInformationException.class)
			
	}
	
}