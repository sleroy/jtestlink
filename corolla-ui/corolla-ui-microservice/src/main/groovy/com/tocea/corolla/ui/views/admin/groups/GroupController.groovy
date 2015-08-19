/*
 * Corolla - A Tool to manage software requirements and test cases 
 * Copyright (C) 2015 Tocea
 * 
 * This file is part of Corolla.
 * 
 * Corolla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, 
 * or any later version.
 * 
 * Corolla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Corolla.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tocea.corolla.ui.views.admin.groups

import javax.validation.Valid;

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.users.commands.CreateUserGroupCommand
import com.tocea.corolla.users.commands.EditUserGroupCommand
import com.tocea.corolla.users.dao.IUserDAO;
import com.tocea.corolla.users.dao.IUserGroupDAO;
import com.tocea.corolla.users.domain.UserGroup
import com.tocea.corolla.users.service.UserDtoService;

@Controller
@Slf4j
@PreAuthorize("@userAuthorization.hasAdminAccess()")
class GroupController {

	def static final INDEX_PAGE = "admin/groups"
	def static final EDIT_PAGE = "admin/groups_edit"
	
	@Autowired
	private IUserGroupDAO groupDAO
	
	@Autowired
	private UserDtoService userDTODAO
	
	@Autowired
	private IUserDAO userDAO
	
	@Autowired
	private Gate gate;
	
	@ModelAttribute("menu")
	public String setMenu() {
		return "admin"
	}
	
	@RequestMapping("/ui/admin/groups")
	public ModelAndView getGroups() {
		
		def model = new ModelAndView(INDEX_PAGE)		
		model.addObject "groups", groupDAO.findAll()
		
		return model
		
	}
	
	@RequestMapping("/ui/admin/groups/add")
	public ModelAndView getAddPage() {
		
		return getFormModelAndView(null)
		
	}
	
	@RequestMapping(value = "/ui/admin/groups/add", method = RequestMethod.POST)
	public ModelAndView addGroup(@Valid @ModelAttribute("group") UserGroup group, BindingResult _result) {
		
		group = _result.model.get("group")
				
		if (_result.hasErrors()) {			
			return getFormModelAndView(group)			
		}
		
		this.gate.dispatch new CreateUserGroupCommand(group)
		
		return new ModelAndView("redirect:/ui/admin/groups")
		
	}
	
	@RequestMapping("/ui/admin/groups/edit/{id}")
	public ModelAndView getEditPage(@PathVariable id) {
		
		UserGroup group = groupDAO.findOne(id)
				
		return getFormModelAndView(group)
		
	}
	
	@RequestMapping(value = "/ui/admin/groups/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editGroup(@PathVariable id, @Valid @ModelAttribute("group") UserGroup group, BindingResult _result) {
		
		group = _result.model.get("group")
		log.info("{}", group.userIds)
					
		if (_result.hasErrors()) {		
			return getFormModelAndView(group)		
		}
		
		this.gate.dispatch new EditUserGroupCommand(group)
		
		return new ModelAndView("redirect:/ui/admin/groups")
		
	}
	
	private ModelAndView getFormModelAndView(UserGroup group) {
		
		def users = userDTODAO.getUsersWithRoleList()
				
		def model = new ModelAndView(EDIT_PAGE)
		model.addObject "group", group != null ? group : new UserGroup()
		model.addObject "users", users
		
		if (group != null && group.userIds != null) {
			model.addObject "users_in_group", users.findAll { group.userIds.contains(it.login) }
		}
		
		return model
	}
	
}
