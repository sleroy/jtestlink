package com.tocea.corolla.ui.views.projects

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@Slf4j
class RequirementPageController {

	private static String PROJECT_VIEW = "project/project"
	private static String REVISION_VIEW = "project/revision"
	
	@ModelAttribute("menu")
	public setMenu() {
		return "requirements"
	}
	
	@RequestMapping("/ui/requirements/{project_key}")
	public ModelAndView getRequirementsPage(@PathVariable project_key) {
		
		def model = new ModelAndView(PROJECT_VIEW)
		model.addObject "project", project_key
		
		return model
	}
	
	@RequestMapping("/ui/requirements/{project_key}/{req_id}")
	public ModelAndView getRequirementPage(@PathVariable project_key, @PathVariable req_id) {
		
		def model = new ModelAndView(PROJECT_VIEW)
		model.addObject "project", project_key
		
		return model
	}
	
	@RequestMapping("/ui/requirements/{project_key}/{req_id}/revisions/{rev_id}")
	public ModelAndView getRevisionPage(@PathVariable project_key, @PathVariable req_id, @PathVariable rev_id) {
		
		def model = new ModelAndView(REVISION_VIEW)
		model.addObject "project", project_key
		
		return model
		
	}
	
}
