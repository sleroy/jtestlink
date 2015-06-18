package com.tocea.corolla.ui.views.projects

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
class ProjectPageController {

	@ModelAttribute("sideMenu")
	public String addAccount() {
		return "projectMenu"
	}
	
	@RequestMapping("/ui/projects/{id}")
	public ModelAndView getProjectPage(@PathVariable id) {
		
		return new ModelAndView("project/project")
	}
	
}
