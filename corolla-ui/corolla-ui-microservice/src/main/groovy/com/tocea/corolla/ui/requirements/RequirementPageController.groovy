package com.tocea.corolla.ui.requirements

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.products.domain.ProjectBranch
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException
import com.tocea.corolla.products.exceptions.ProjectNotFoundException
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement
import com.tocea.corolla.requirements.exceptions.RequirementNotFoundException
import com.tocea.corolla.revisions.domain.IChange
import com.tocea.corolla.revisions.domain.ICommit
import com.tocea.corolla.revisions.services.IRevisionService;

@Controller
@Slf4j
class RequirementPageController {

	private static String PROJECT_VIEW = "project/project"
	private static String REVISION_VIEW = "project/revision"
	
	@Autowired
	private IRevisionService revisionService;
	
	@Autowired
	private IRequirementDAO requirementDAO;
	
	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@ModelAttribute("menu")
	public setMenu() {
		return "requirements"
	}
	
	@RequestMapping("/ui/requirements/{project_key}")
	public ModelAndView getRequirementsPage(@PathVariable project_key) {
		
		def model = new ModelAndView(PROJECT_VIEW)
		model.addObject "project", project_key
		
		return model
	}
	
	@RequestMapping("/ui/requirements/{project_key}/{req_id}")
	public ModelAndView getRequirementPage(@PathVariable project_key, @PathVariable req_id) {
		
		def model = new ModelAndView(PROJECT_VIEW)
		model.addObject "project", project_key
		
		return model
	}
	
	@RequestMapping("/ui/requirements/{project_key}/{req_id}/revisions/{rev_id}")
	public ModelAndView getRevisionPage(@PathVariable project_key, @PathVariable req_id, @PathVariable rev_id) {
		
		def model = new ModelAndView(REVISION_VIEW)
		model.addObject "project", project_key
		
		return model
		
	}
	
	@RequestMapping("/ui/requirements/{projectKey}/{branchName}/{requirementKey}/revisions/{commitID}")
	public ModelAndView getRevisionPage(@PathVariable projectKey, @PathVariable branchName, @PathVariable requirementKey, @PathVariable commitID) {
		
		def model = new ModelAndView(REVISION_VIEW)
		model.addObject "project", projectKey
		
		Project project = projectDAO.findByKey(projectKey)
		
		if (project == null) {
			throw new ProjectNotFoundException()
		}
		
		ProjectBranch branch = branchDAO.findByNameAndProjectId(branchName, project.id)
				
		if (branch == null) {
			throw new ProjectBranchNotFoundException()
		}
				
		Requirement requirement = requirementDAO.findByKeyAndProjectBranchId(requirementKey, branch.id)
				
		if (requirement == null) {
			throw new RequirementNotFoundException()
		}
		
		Collection<ICommit> commits = revisionService.getHistory(requirement.id, Requirement.class)
		
		commits.each { log.info it.id }

		def commit = revisionService.findCommitByID(requirement.id, Requirement.class, commitID)
		def previousCommit = revisionService.getPreviousCommit(requirement.id, Requirement.class, commitID)	
		
		def version = revisionService.getSnapshot(commit)
		def oldVersion = previousCommit != null ? revisionService.getSnapshot(previousCommit) : new Requirement(id: requirement.id)
		
		def changes = revisionService.compare oldVersion, version
		
		model.addObject "commit", commit
		model.addObject "previousCommit", previousCommit
		model.addObject "changes", changes
		
		return model	
	}
	
}
