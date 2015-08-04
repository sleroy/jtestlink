package com.tocea.corolla.requirements.commands.handlers;

import org.junit.Rule;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.requirements.commands.DeleteRequirementCommand
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "DELETE_REQUIREMENT")
public class DeleteRequirementCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementDAO requirementDAO = Mock(IRequirementDAO)	
	def Gate gate = Mock(Gate)
	def DeleteRequirementCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	
	def setup() {
		handler = new DeleteRequirementCommandHandler(
				requirementDAO : requirementDAO
		)
	}
	
	def "it should delete a requirement"() {
		
		given:
			def req = new Requirement()
			req.id = "1"
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new DeleteRequirementCommand(req.id)
	
		then:
			requirementDAO.findOne(req.id) >> req
			notThrown(Exception.class)
			1 * requirementDAO.delete(req)
			
	}
	
	
	def "it should throw an exception if the requirement does not exist"() {
		
		given:
			def req = new Requirement()
			req.id = "1"
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new DeleteRequirementCommand(req.id)
	
		then:
			requirementDAO.findOne(req.id) >> null
			thrown(RequirementNotFoundException.class)
			0 * requirementDAO.delete(_)
			
	}
	
	def "it should throw an exception if the requirement ID is missing"() {
		
		given:
			def req = new Requirement(id: "")
		
		when:
			handler.handle new DeleteRequirementCommand(req.id)
	
		then:
			thrown(MissingRequirementInformationException.class)
			0 * requirementDAO.delete(_)
		
	}
	
}