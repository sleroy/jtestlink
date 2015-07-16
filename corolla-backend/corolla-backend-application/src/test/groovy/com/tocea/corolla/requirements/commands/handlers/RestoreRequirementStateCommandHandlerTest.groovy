package com.tocea.corolla.requirements.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.dao.IProjectBranchDAO
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException;
import com.tocea.corolla.requirements.commands.EditRequirementCommand;
import com.tocea.corolla.requirements.commands.RestoreRequirementStateCommand
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.revisions.exceptions.MissingCommitInformationException;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "RESTORE_REQUIREMENT_STATE")
class RestoreRequirementStateCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IRequirementDAO requirementDAO = Mock(IRequirementDAO)	
	def RestoreRequirementStateCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	def Gate gate = Mock(Gate)
	
	def setup() {
		handler = new RestoreRequirementStateCommandHandler(
				requirementDAO : requirementDAO,
				revisionService : revisionService,
				gate : gate
		)
	}
	
	def "it should restore a requirement to its previous state"() {
		
		given:
			def req = new Requirement(id: "1")
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
			
			def previousState = new Requirement(id: "1")
			previousState.key = "ADD_SOMETHING"
			previousState.name = "Add something"
			previousState.projectBranchId = "12"
			
			def commitID = "2"
		
		when:
			handler.handle new RestoreRequirementStateCommand(req.id, commitID)
	
		then:
			1 * requirementDAO.findOne(req.id) >> req
			notThrown(Exception.class)
			1 * revisionService.getSnapshot(req.id, Requirement.class, commitID) >> previousState
			1 * gate.dispatch { it instanceof EditRequirementCommand && it.requirement == previousState }	
			
	}
	
	def "it should throw an exception if the requirement ID is missing"() {
		
		given:
			def req = new Requirement(id: "")			
			def commitID = "2"
		
		when:
			handler.handle new RestoreRequirementStateCommand(req.id, commitID)
	
		then:
			thrown(MissingRequirementInformationException.class)
		
	}
	
	def "it should throw an exception if the commit ID is missing"() {
		
		given:
			def req = new Requirement(id: "1")
			def commitID = ""
		
		when:
			handler.handle new RestoreRequirementStateCommand(req.id, commitID)
	
		then:
			thrown(MissingCommitInformationException.class)
		
	}
	
	def "it should throw if the requirement does not exist"() {
		
		given:
			def req = new Requirement(id: "1")
			def commitID = "2"
		
		when:
			handler.handle new RestoreRequirementStateCommand(req.id, commitID)
	
		then:
			1 * requirementDAO.findOne(req.id) >> null
		
		then:
			thrown(RequirementNotFoundException.class)
		
	}
	
}