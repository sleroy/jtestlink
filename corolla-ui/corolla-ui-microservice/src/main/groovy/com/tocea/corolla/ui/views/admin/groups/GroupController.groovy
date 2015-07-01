package com.tocea.corolla.ui.views.admin.groups

import groovy.util.logging.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;

@Controller
@Slf4j
class GroupController {

	def static final INDEX_PAGE = "admin/groups"
	
	
	@RequestMapping("/ui/admin/groups")
	public ModelAndView getGroups() {
		
		def model = new ModelAndView(INDEX_PAGE)
		
		model.addObject "groups", Lists.newArrayList()
		
		return model
		
	}
	
}
