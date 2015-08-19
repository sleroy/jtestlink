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
package com.tocea.corolla.ui.views.admin.roles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.users.commands.DuplicateRoleCommand
import com.tocea.corolla.users.commands.MarksRoleAsDefaultCommand
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.validation.UserValidation

/**
 * @author sleroy
 *
 */
@PreAuthorize("@userAuthorization.hasAdminAccess()")
@Controller
public class RoleAdminController {


	@Autowired
	private IRoleDAO roleDAO

	@Autowired
	private UserValidation validation
	
	@Autowired
	private Gate gate

	@ModelAttribute("menu")
	public String setMenu() {
		return "admin"
	}

	@RequestMapping("/ui/admin/roles")
	public ModelAndView getHomePage() {
		
		def ModelAndView model = new ModelAndView("admin/roles")
		model.addObject "roles", roleDAO.findAll()

		return model
	}

	@RequestMapping("/ui/admin/roles/default/{id}")
	public String marksRoleAsDefault(@PathVariable String id) {
		
		if (roleDAO.findOne(id) != null) {
			gate.dispatch(new MarksRoleAsDefaultCommand(id))
		}
		
		return "redirect:/ui/admin/roles"
		
	}

	@RequestMapping("/ui/admin/roles/duplicate/{id}")
	public String duplicateRole(@PathVariable String id) {
		
		if (roleDAO.findOne(id) != null) {
			gate.dispatch(new DuplicateRoleCommand(id))
		}
		
		return "redirect:/ui/admin/roles"
	}
	
}