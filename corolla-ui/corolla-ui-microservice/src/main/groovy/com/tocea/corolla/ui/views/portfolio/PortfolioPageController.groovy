package com.tocea.corolla.ui.views.portfolio

import javax.validation.Valid;

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Function
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.portfolio.dto.ProjectNodeDTO
import com.tocea.corolla.products.commands.CreateProjectCommand
import com.tocea.corolla.products.commands.EditProjectCommand
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectCategoryDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.dao.IProjectStatusDAO;
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.products.exceptions.InvalidProjectInformationException
import com.tocea.corolla.products.exceptions.ProjectNotFoundException
import com.tocea.corolla.products.utils.ProjectUtils;
import com.tocea.corolla.revisions.exceptions.InvalidCommitInformationException
import com.tocea.corolla.revisions.services.IRevisionService;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.domain.Permission;
import com.tocea.corolla.users.dto.UserDto;
import com.tocea.corolla.users.service.IUserDtoService

@Controller
@Slf4j
class PortfolioPageController {
	
	private static final String MANAGER_PAGE 			= "portfolio/manager"
	private static final String PORTFOLIO_PAGE 			= "portfolio/portfolio"
	private static final String MENU_PORTFOLIO 			= "portfolio"
	private static final String MENU_PORTFOLIO_MANAGER 	= "portfolioManager"
	
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
	
	@RequestMapping("/ui/portfolio")
	public ModelAndView getPortfolio() {
		
		def projects = projectDAO.findAll();		
		def tags = ProjectUtils.extractTags(projects);

		def model = new ModelAndView(PORTFOLIO_PAGE)
		model.addObject "menu", MENU_PORTFOLIO		
		model.addObject "projects", projects;
		model.addObject "tags", tags;
		
		return model
	}
	
	@RequestMapping("/ui/portfolio/manager")
	public ModelAndView getPortfolioManagerIndex() {
		
		return buildManagerViewData(new ModelAndView(MANAGER_PAGE), null);
	}
	
	@RequestMapping("/ui/portfolio/manager/{projectKey}")
	public ModelAndView getPortfolioManager(@PathVariable projectKey) {
		
		def project = projectDAO.findByKey(projectKey)
				
		if (project == null) {
			return new ModelAndView("redirect:/ui/portfolio/manager/")
		}
		
		def model = buildManagerViewData(new ModelAndView(MANAGER_PAGE), project)
		
		return model
	}
	
	@RequestMapping(value="/ui/portfolio/manager/create")
	@Secured([Permission.PORTFOLIO_MANAGEMENT])
	public ModelAndView createProject(@Valid @ModelAttribute("newProject") ProjectNodeDTO newProject, BindingResult _result) {
		
		newProject = _result.model.get("newProject")
				
		if (newProject == null) {
			return new ModelAndView("redirect:/ui/portfolio/manager")
		}
		
		if (_result.hasErrors()) {
			def model = buildManagerViewData(new ModelAndView(MANAGER_PAGE), null)
			return model
		}
		
		def command = new CreateProjectCommand();
		command.fromProjectNodeDTO(newProject);
		
		def project = gate.dispatch command;
		
		return new ModelAndView("redirect:/ui/projects/"+project.key)
		
	}
	
	private ModelAndView buildManagerViewData(ModelAndView model, Project project) {
			
		model.addObject "project", project
		model.addObject "status", project ? statusDAO.findOne(project.statusId) : null
		model.addObject "menu", MENU_PORTFOLIO_MANAGER
		model.addObject "folderNodeTypes", folderNodeTypeDAO.findAll()
		model.addObject "newProject", new ProjectNodeDTO()
		
		return model
	}
	
}