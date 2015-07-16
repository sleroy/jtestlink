package com.tocea.corolla.products.commands.handlers

import org.junit.Rule;
import org.mockito.Mockito;

import spock.lang.Specification;

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.products.commands.CreateProjectBranchCommand
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.InvalidProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.products.exceptions.ProjectBranchAlreadyExistException;
import com.tocea.corolla.requirements.commands.CreateRequirementCommand
import com.tocea.corolla.requirements.dao.IRequirementDAO
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.Requirement
import com.tocea.corolla.requirements.domain.RequirementNode
import com.tocea.corolla.requirements.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

@FunctionalTestDoc(requirementName = "ADD_PROJECT_BRANCH")
class CreateProjectBranchCommandHandlerTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def IProjectBranchDAO branchDAO = Mock(IProjectBranchDAO)	
	def IRequirementsTreeDAO requirementsTreeDAO = Mock(IRequirementsTreeDAO)
	def IRequirementDAO requirementDAO = Mock(IRequirementDAO)
	def Gate gate = Mock(Gate)
	def CreateProjectBranchCommandHandler handler
	def IRevisionService revisionService = Mock(IRevisionService)
	
	def setup() {
		handler = new CreateProjectBranchCommandHandler(
				branchDAO : branchDAO,
				requirementsTreeDAO : requirementsTreeDAO,
				requirementDAO : requirementDAO,
				gate : gate
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
	
	def "it should create a new branch from another empty branch"() {
		
		given:
			def origin = new ProjectBranch()
			origin.id = "1"
			origin.name = "Master"
			origin.projectId = "35"
			def originTree = new RequirementsTree(nodes: [])
			
		when:
			handler.handle new CreateProjectBranchCommand("newBranch", origin)
	
		then:
			requirementsTreeDAO.findByBranchId(origin.id) >> originTree
			notThrown(Exception.class)
		
	}
	
	def "it should create a new branch from another non-empty branch"() {
		
		given:
			def origin = new ProjectBranch()
			origin.id = "1"
			origin.name = "Master"
			origin.projectId = "35"
			def originRequirements = [new Requirement(id: "REQ1", name: "req1"), new Requirement(id: "REQ2", name: "req2")]
			def originTree = new RequirementsTree(
					nodes: [
					        new RequirementNode(requirementId: originRequirements[0].id, nodes: []),
					        new RequirementNode(requirementId: originRequirements[1].id, nodes: [])
					]
			)
			
		when:
			handler.handle new CreateProjectBranchCommand("newBranch", origin)
	
		then:
			1 * requirementsTreeDAO.save(_)
			requirementDAO.findByProjectBranchId(origin.id) >> originRequirements
			requirementsTreeDAO.findByBranchId(origin.id) >> originTree		
			1 * gate.dispatch { 
				it instanceof CreateRequirementCommand && it.requirement.name == originRequirements[0].name && it.requirement.id != originRequirements[0].id 
			}
			1 * gate.dispatch { 
				it instanceof CreateRequirementCommand && it.requirement.name == originRequirements[1].name && it.requirement.id != originRequirements[1].id 
			}
			1 * requirementsTreeDAO.save { it.nodes.size() == 2 }
			notThrown(Exception.class)
			
	}
	
	def "it should throw an exception if the requirements tree of the origin branch cannot be found"() {
		
		given:
			def origin = new ProjectBranch()
			origin.id = "1"
			origin.name = "Master"
			origin.projectId = "35"
			def originRequirements = [new Requirement(id: "REQ1", name: "req1"), new Requirement(id: "REQ2", name: "req2")]
			def originTree = null
			
		when:
			handler.handle new CreateProjectBranchCommand("newBranch", origin)
	
		then:
			requirementDAO.findByProjectBranchId(origin.id) >> originRequirements
			requirementsTreeDAO.findByBranchId(origin.id) >> originTree
			thrown(RequirementsTreeNotFoundException.class)
		
	}
	
}