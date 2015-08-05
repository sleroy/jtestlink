package com.tocea.corolla.ui.views.projects;

import groovy.util.logging.Slf4j;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.EditProjectCommand
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectCategoryDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.dao.IProjectStatusDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.exceptions.ProjectNotFoundException
import com.tocea.corolla.revisions.exceptions.InvalidCommitInformationException;
import com.tocea.corolla.revisions.services.IRevisionService;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.dto.UserDto

@Controller
@Slf4j
public class ProjectDetailsPageController {

	private static final String DETAILS_VIEW = "project/details"
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired
	private IProjectStatusDAO statusDAO;
	
	@Autowired
	private IProjectCategoryDAO projectCategoryDAO;
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IRevisionService revisionService;
	
	@Autowired
	private Gate gate;

	@RequestMapping(value="/ui/projects/{projectKey}")
	public ModelAndView getProjectDetails(@PathVariable projectKey) {
		
		def project = projectDAO.findByKey(projectKey)
		
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		
		def model = buildManagerViewData(new ModelAndView(DETAILS_VIEW), project)
		
		return model
	}
	
	@RequestMapping(value = "/ui/projects/{projectID}/edit", method = RequestMethod.POST)
	public ModelAndView editProject(@PathVariable String projectID, @Valid @ModelAttribute("project") Project project, BindingResult _result) {
		
		project = _result.model.get("project")
		
		if (project == null) {
			throw new ProjectNotFoundException()
		}
		
		if (_result.hasErrors()) {
			log.info "error found in project data : {}", _result.fieldErrors;
			def model = buildManagerViewData(new ModelAndView(DETAILS_VIEW), project)
			model.addObject "selectedTab", "edit"
			return model
		}
		
		gate.dispatch new EditProjectCommand(project);
		
		return new ModelAndView("redirect:/ui/projects/"+project.key)
		
	}
	
	private ModelAndView buildManagerViewData(ModelAndView model, Project project) {
		
		def commits = revisionService.getHistory(project.id, project.class)				
		def owner = project.ownerId ? userDAO.findOne(project.ownerId) : null
			
		model.addObject "project", project
		model.addObject "status", statusDAO.findOne(project.statusId)
		model.addObject "category", project.categoryId ? projectCategoryDAO.findOne(project.categoryId) : null
		model.addObject "owner", owner ? new UserDto(owner) : null
		model.addObject "menu", "projectDetails"
		model.addObject "folderNodeTypes", folderNodeTypeDAO.findAll()
		model.addObject "branches", branchDAO.findByProjectId(project.id)
		model.addObject "commits", commits
		model.addObject "statuses", statusDAO.findAll()
		model.addObject "categories", projectCategoryDAO.findAll()
		
		return model
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ExceptionHandler([
	       InvalidCommitInformationException.class, 
	       ProjectNotFoundException.class
	])
	public void handlePageNotFoundException() {
		
	}
	
}
