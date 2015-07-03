package com.tocea.corolla.ui.views.projects

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
class ReleasePageController {

	private static String RELEASES_VIEW = "project/releases"
	
	@ModelAttribute("menu")
	public String setMenu() {
		return "releases"
	}
			
	@RequestMapping("/ui/releases/{project_key}")
	public ModelAndView getReleasePage(@PathVariable project_key) {
		
		def model = new ModelAndView(RELEASES_VIEW)
		model.addObject "project", project_key
		
		return model
		
	}
	
}
