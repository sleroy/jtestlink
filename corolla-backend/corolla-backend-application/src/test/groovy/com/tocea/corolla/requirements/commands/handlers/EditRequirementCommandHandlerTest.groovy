package com.tocea.corolla.requirements.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.products.dao.IProjectBranchDAO
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException;
import com.tocea.corolla.requirements.commands.EditRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "EDIT_REQUIREMENT")
class EditRequirementCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementDAO requirementDAO = Mock(IRequirementDAO)	
	def EditRequirementCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	
	def setup() {
		handler = new EditRequirementCommandHandler(
				requirementDAO : requirementDAO,
				revisionService : revisionService
		)
	}
	
	def "it should edit a requirement"() {
		
		given:
			def req = new Requirement(id: "1")
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new EditRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> null
			notThrown(Exception.class)
			1 * requirementDAO.save(req)		
	}
	
	def "it should edit a requirement without changing its key"() {
		
		given:
			def req = new Requirement(id: "1")
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new EditRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> req
			notThrown(Exception.class)
			1 * requirementDAO.save(req)		
	}
	
	def "it should call the revision service when editing a requirement"() {
		
		given:
			def req = new Requirement(id: "1")
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new EditRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> null
			notThrown(Exception.class)
			1 * revisionService.commit(req)	
		
	}
	
	def "it should not have two requirements with the same key on the same branch"() {
		
		given:
			def req = new Requirement(id: "1")
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
			def sameReq = new Requirement(id: "2", key: req.key, name: req.name, projectBranchId: req.projectBranchId)
		
		when:
			handler.handle new EditRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> sameReq
			0 * requirementDAO.save(_)
			thrown(RequirementAlreadyExistException.class)
		
	}
	
	def "it should throw an exception if the requirement is null"() {
		
		given:
			def req = null
		
		when:
			handler.handle new EditRequirementCommand(req)
	
		then:
			0 * requirementDAO.save(_)
			thrown(MissingRequirementInformationException.class)
			
	}
	
	def "it should throw an exception if the ID is not defined"() {
		
		given:
			def req = new Requirement(id: "")
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new EditRequirementCommand(req)
	
		then:
			0 * requirementDAO.save(_)
			thrown(InvalidRequirementInformationException.class)
			
	}
	
	def "it should throw an exception if the branch ID is not defined"() {
		
		given:
			def req = new Requirement(id: "1")
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = ""
		
		when:
			handler.handle new EditRequirementCommand(req)
	
		then:
			thrown(InvalidRequirementInformationException.class)
			0 * requirementDAO.save(req)
			
	}
	
}