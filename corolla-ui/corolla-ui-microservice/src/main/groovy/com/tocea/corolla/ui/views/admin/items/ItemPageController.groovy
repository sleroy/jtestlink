package com.tocea.corolla.ui.views.admin.items

import groovy.util.logging.Slf4j;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute

import com.tocea.corolla.users.permissions.Permissions;

@Controller
@Slf4j
@PreAuthorize("@userAuthorization.hasAdminAccess()")
class ItemPageController {

	@ModelAttribute("menu")
	public String setMenu() {
		return "admin"
	}
	
	@RequestMapping('/ui/admin/items') 
	public ModelAndView getItemsList() {
		
		def model = new ModelAndView("admin/items")		
		return model
		
	}
	
}
