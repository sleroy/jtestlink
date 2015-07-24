package com.tocea.corolla.ui.views.portfolio

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tocea.corolla.products.dao.IProjectDAO;

@Controller
@Slf4j
class PortfolioPageController {
	
	private static final String MANAGER_PAGE = "portfolio/manager";
	private static final String PORTFOLIO_PAGE = "portfolio/portfolio";

	@Autowired
	private IProjectDAO projectDAO;
	
	@RequestMapping("/ui/portfolio")
	public ModelAndView getPortfolio() {
		
		def model = new ModelAndView(PORTFOLIO_PAGE)
		model.addObject "menu", "portfolio"
		
		model.addObject "projects", projectDAO.findAll();
		
		return model
	}
	
	@RequestMapping("/ui/portfolio/manager")
	public ModelAndView getPortfolioManagerIndex() {
		
		def model = new ModelAndView(MANAGER_PAGE);
		model.addObject "menu", "portfolioManager"
		
		return model
	}
	
	@RequestMapping("/ui/portfolio/manager/{project_key}")
	public ModelAndView getPortfolioManager(@PathVariable project_key) {
		
		def model = new ModelAndView(MANAGER_PAGE);
		model.addObject "project", project_key
		model.addObject "menu", "portfolioManager"
		
		return model
	}
	
}
