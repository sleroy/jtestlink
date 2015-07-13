package com.tocea.corolla.requirements.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.dao.IProjectBranchDAO
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException;
import com.tocea.corolla.requirements.commands.CreateRequirementCommand;
import com.tocea.corolla.requirements.commands.CreateRequirementTreeNodeCommand;
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
	def IProjectBranchDAO branchDAO = Mock(IProjectBranchDAO)
	def Gate gate = Mock(Gate)
	def CreateRequirementCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	
	def setup() {
		handler = new CreateRequirementCommandHandler(
				requirementDAO : requirementDAO,
				branchDAO : branchDAO,
				gate: gate,
				revisionService : revisionService
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
			branchDAO.findOne(req.projectBranchId) >> new ProjectBranch(id: req.projectBranchId)
			notThrown(Exception.class)
			1 * requirementDAO.save(req)		
	}
	
	def "it should call the revision service when creating a new requirement"() {
		
		given:
			def req = new Requirement()
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new CreateRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> null
			branchDAO.findOne(req.projectBranchId) >> new ProjectBranch(id: req.projectBranchId)
			notThrown(Exception.class)
			1 * revisionService.commit(req)	
		
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
	
	def "it should throw an exception if the branch cannot be found"() {
		
		given:
			def req = new Requirement()
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
		
		when:
			handler.handle new CreateRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> null
			branchDAO.findOne(req.projectBranchId) >> null
			thrown(ProjectBranchNotFoundException.class)
			0 * requirementDAO.save(req)
			
	}
	
	def "it should dispatch a command to create a node in the requirement tree"() {
		
		given:
			def req = new Requirement()
			req.key = "ADD_REQUIREMENT"
			req.name = "Add a requirement"
			req.projectBranchId = "12"
			def branch = new ProjectBranch(id: req.projectBranchId)
		
		when:
			handler.handle new CreateRequirementCommand(req)
	
		then:
			requirementDAO.findByKeyAndProjectBranchId(req.key, req.projectBranchId) >> null
			branchDAO.findOne(req.projectBranchId) >> branch
			notThrown(Exception.class)
			1 * gate.dispatch({ it instanceof CreateRequirementTreeNodeCommand  && it.branch == branch && it.requirementId == req.id })
		
	}
	
}