package com.tocea.corolla.ui.views.projects

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
class ProjectPageController {

	private static String PROJECT_VIEW = "project/project"
	private static String REVISION_VIEW = "project/revision"
	
	@RequestMapping("/ui/projects/{id}")
	public ModelAndView getProjectPage(@PathVariable id) {
		
		def model = new ModelAndView(PROJECT_VIEW)
		model.addObject "project", id
		
		return model
	}
	
	@RequestMapping("/ui/projects/{project_key}/requirements/{req_id}/revisions/{rev_id}")
	public ModelAndView getRevisionPage(@PathVariable project_key, @PathVariable req_id, @PathVariable rev_id) {
		
		def model = new ModelAndView(REVISION_VIEW)
		model.addObject "project", project_key
		
		return model
		
	}
	
}
