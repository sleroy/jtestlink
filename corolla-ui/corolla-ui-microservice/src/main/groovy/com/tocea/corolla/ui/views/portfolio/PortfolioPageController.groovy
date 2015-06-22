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
	
	def static final String INDEX_PAGE = "portfolio/index";

	@RequestMapping("/ui/portfolio")
	public ModelAndView getPortfolioPage() {
		
		return new ModelAndView(INDEX_PAGE);
	}
	
	@RequestMapping("/ui/portfolio/{project_key}")
	public ModelAndView getPortfolioPage(@PathVariable project_key) {
		
		return new ModelAndView(INDEX_PAGE);
	}
	
}
