package com.tocea.corolla.ui.views.portfolio

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
class PortfolioPageController {
	
	def static final String MANAGER_PAGE = "portfolio/manager";
	def static final String PORTFOLIO_PAGE = "portfolio/portfolio";

	@RequestMapping("/ui/portfolio")
	public ModelAndView getPortfolio() {
		
		return new ModelAndView(PORTFOLIO_PAGE);
	}
	
	@RequestMapping("/ui/portfolio/manager")
	public ModelAndView getPortfolioManagerIndex() {
		
		return new ModelAndView(MANAGER_PAGE);
	}
	
	@RequestMapping("/ui/portfolio/manager/{project_key}")
	public ModelAndView getPortfolioManager(@PathVariable project_key) {
		
		def model = new ModelAndView(MANAGER_PAGE);
		model.addObject "project", project_key
		
		return model
	}
	
}
