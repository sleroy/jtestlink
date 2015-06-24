package com.tocea.corolla.ui.views.admin.items

import groovy.util.logging.Slf4j;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tocea.corolla.users.domain.Permission;

@Controller
@Slf4j
class ItemPageController {

	@Secured(Permission.ADMIN)
	@RequestMapping('/ui/admin/items') 
	public ModelAndView getItemsList() {
		
		def model = new ModelAndView("admin/items")		
		return model
		
	}
	
}
