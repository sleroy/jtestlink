package com.tocea.corolla.ui.views.projects

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
class VariablePageController {

	private static String VARIABLES_VIEW = "project/variables"
			
	@ModelAttribute("menu")
	public String setMenu() {
		return "variables"
	}
			
	@RequestMapping("/ui/variables/{project_key}")
	public ModelAndView getVariablesPage(@PathVariable project_key) {
		
		def model = new ModelAndView(VARIABLES_VIEW)
		model.addObject "project", project_key
		
		return model
		
	}
	
}
