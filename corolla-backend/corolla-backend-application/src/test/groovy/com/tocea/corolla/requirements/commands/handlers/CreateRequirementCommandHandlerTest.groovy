package com.tocea.corolla.requirements.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.requirements.commands.CreateRequirementCommand;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_REQUIREMENT")
class CreateRequirementCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementDAO requirementDAO = Mock(IRequirementDAO)	
	def CreateRequirementCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	
	def setup() {
		handler = new CreateRequirementCommandHandler(
				requirementDAO : requirementDAO
		)
	}
	
	def "it should create a new requirement"() {
		
		given:
			def req = new Requirement()
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new CreateRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> null
			notThrown(Exception.class)
			1 * requirementDAO.save(req)		
	}
	
	def "it should not create two requirements with the same key on the same branch"() {
		
		given:
			def req = new Requirement()
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
			def sameReq = new Requirement(key: req.key, name: req.name, projectBranchId: req.projectBranchId)
		
		when:
			handler.handle new CreateRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> sameReq
			0 * requirementDAO.save(_)
			thrown(RequirementAlreadyExistException.class)
		
	}
	
	def "it should throw an exception if the requirement is null"() {
		
		given:
			def req = null
		
		when:
			handler.handle new CreateRequirementCommand(req)
	
		then:
			0 * requirementDAO.save(_)
			thrown(MissingRequirementInformationException.class)
			
	}
	
	def "it should throw an exception if the ID is already defined"() {
		
		given:
			def req = new Requirement()
			req.id = "1"
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new CreateRequirementCommand(req)
	
		then:
			0 * requirementDAO.save(_)
			thrown(InvalidRequirementInformationException.class)
			
	}
	
}