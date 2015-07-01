package com.tocea.corolla.ui.views.admin.groups

import javax.validation.Valid;

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.dao.UserDtoService;
import com.tocea.corolla.users.domain.UserGroup

@Controller
@Slf4j
class GroupController {

	def static final INDEX_PAGE = "admin/groups"
	def static final EDIT_PAGE = "admin/groups_edit"
	
	@Autowired
	private IUserGroupDAO groupDAO
	
	@Autowired
	private UserDtoService userDTODAO
	
	@Autowired
	private IUserDAO userDAO
	
	@RequestMapping("/ui/admin/groups")
	public ModelAndView getGroups() {
		
		def model = new ModelAndView(INDEX_PAGE)
		
		model.addObject "groups", groupDAO.findAll()
		
		return model
		
	}
	
	@RequestMapping("/ui/admin/groups/add")
	public ModelAndView getAddPage() {
		
		def model = new ModelAndView(EDIT_PAGE)
		model.addObject "group", new UserGroup()
		model.addObject "users", userDTODAO.getUsersWithRoleList()	
		
		return model
		
	}
	
	@RequestMapping(value = "/ui/admin/groups/add", method = RequestMethod.POST)
	public ModelAndView addGroup(@Valid @ModelAttribute group, BindingResult _result) {
		
		if (_result.hasErrors()) {
			
			def model = new ModelAndView(EDIT_PAGE)
			model.addObject "group", new UserGroup()
			model.addObject "users", userDTODAO.getUsersWithRoleList()
			
			return model
			
		}
		
		
		
		return new ModelAndView(INDEX_PAGE)
		
	}
	
}
