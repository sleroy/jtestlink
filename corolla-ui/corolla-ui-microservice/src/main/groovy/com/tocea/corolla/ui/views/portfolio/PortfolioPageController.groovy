package com.tocea.corolla.ui.views.portfolio

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project
import com.tocea.corolla.products.exceptions.ProjectNotFoundException
import com.tocea.corolla.revisions.exceptions.InvalidCommitInformationException
import com.tocea.corolla.revisions.services.IRevisionService;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;

@Controller
@Slf4j
class PortfolioPageController {
	
	private static final String MANAGER_PAGE 			= "portfolio/manager"
	private static final String PORTFOLIO_PAGE 			= "portfolio/portfolio"
	private static final String REVISION_VIEW 			= "portfolio/revision"
	private static final String MENU_PORTFOLIO 			= "portfolio"
	private static final String MENU_PORTFOLIO_MANAGER 	= "portfolioManager"
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Autowired
	private IProjectBranchDAO branchDAO;
	
	@Autowired
	private IRevisionService revisionService;
	
	@RequestMapping("/ui/portfolio")
	public ModelAndView getPortfolio() {
		
		def model = new ModelAndView(PORTFOLIO_PAGE)
		model.addObject "menu", MENU_PORTFOLIO
		
		model.addObject "projects", projectDAO.findAll();
		
		return model
	}
	
	@RequestMapping("/ui/portfolio/manager")
	public ModelAndView getPortfolioManagerIndex() {
		
		def model = new ModelAndView(MANAGER_PAGE);
		model.addObject "menu", MENU_PORTFOLIO_MANAGER
		model.addObject "folderNodeTypes", folderNodeTypeDAO.findAll()
		
		return model
	}
	
	@RequestMapping("/ui/portfolio/manager/{projectKey}")
	public ModelAndView getPortfolioManager(@PathVariable projectKey) {
		
		def project = projectDAO.findByKey(projectKey)
				
		if (project == null) {
			return new ModelAndView("redirect:/ui/portfolio/manager/")
		}
		
		def commits = revisionService.getHistory(project.id, project.class)
		
		def model = new ModelAndView(MANAGER_PAGE);
		model.addObject "project", project
		model.addObject "menu", MENU_PORTFOLIO_MANAGER
		model.addObject "folderNodeTypes", folderNodeTypeDAO.findAll()
		model.addObject "branches", branchDAO.findByProjectId(project.id)
		model.addObject "commits", commits
		
		return model
	}
	
	@RequestMapping("/ui/portfolio/manager/{projectKey}/revisions/{commitID}")
	public ModelAndView getRevisionPage(@PathVariable projectKey, @PathVariable commitID) {
		
		def project = projectDAO.findByKey(projectKey)
				
		if (project == null) {
			throw new ProjectNotFoundException();
		}
				
		def commit = revisionService.findCommitByID(project.id, project.class, commitID)
		
		if (commit == null) {
			throw new InvalidCommitInformationException("No commit associated to this ID");
		}
		
		def previousCommit = revisionService.getPreviousCommit(project.id, project.class, commitID)	
		
		def version = revisionService.getSnapshot(commit)
		def oldVersion = previousCommit != null ? revisionService.getSnapshot(previousCommit) : new Project(id: project.id)
		
		def changes = revisionService.compare oldVersion, version
		
		def model = new ModelAndView(REVISION_VIEW)
		model.addObject "project", project
		model.addObject "commit", commit
		model.addObject "previousCommit", previousCommit
		model.addObject "changes", changes
		
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