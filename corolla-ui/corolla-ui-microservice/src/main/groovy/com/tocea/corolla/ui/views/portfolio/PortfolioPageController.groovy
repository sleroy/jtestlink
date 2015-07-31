package com.tocea.corolla.ui.views.portfolio

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;

@Controller
@Slf4j
class PortfolioPageController {
	
	private static final String MANAGER_PAGE = "portfolio/manager";
	private static final String PORTFOLIO_PAGE = "portfolio/portfolio";
	private static final String MENU_PORTFOLIO = "portfolio";
	private static final String MENU_PORTFOLIO_MANAGER = "portfolioManager";
	
	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
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
			return new ModelAndView("redirect:/ui/portfolio/manager")
		}
		
		def model = new ModelAndView(MANAGER_PAGE);
		model.addObject "project", project
		model.addObject "menu", MENU_PORTFOLIO_MANAGER
		model.addObject "folderNodeTypes", folderNodeTypeDAO.findAll()
		
		return model
	}
	
}
