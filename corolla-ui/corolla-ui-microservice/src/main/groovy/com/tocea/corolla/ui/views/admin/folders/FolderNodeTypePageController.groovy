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
package com.tocea.corolla.ui.views.admin.folders

import groovy.util.logging.Slf4j;

import javax.validation.Valid

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.trees.commands.CreateFolderNodeTypeCommand
import com.tocea.corolla.trees.commands.EditFolderNodeTypeCommand
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNodeType

@Controller
@Slf4j
class FolderNodeTypePageController {

	private static final String INDEX_PAGE = "admin/folders";
	private static final String FORM_PAGE = "admin/folders_edit";
	
	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Autowired
	private Gate gate
	
	@ModelAttribute("menu")
	public String setMenu() {
		return "admin"
	}
		
	@RequestMapping('/ui/admin/folder-node-types') 
	@PreAuthorize("@userAuthorization.hasAdminAccess()")
	public ModelAndView getListPage() {
		
		def model = new ModelAndView(INDEX_PAGE)	
		model.addObject "types", folderNodeTypeDAO.findAll()
		
		return model	
	}
	
	@RequestMapping('/ui/admin/folder-node-types/add') 
	@PreAuthorize("@userAuthorization.hasAdminAccess()")
	public ModelAndView getAddPage() {
		
		def model = new ModelAndView(FORM_PAGE)	
		model.addObject "type", new FolderNodeType()
		
		return model	
	}
	
	@RequestMapping(value = '/ui/admin/folder-node-types/add', method = RequestMethod.POST) 
	@PreAuthorize("@userAuthorization.hasAdminAccess()")
	public ModelAndView addFolder(@Valid @ModelAttribute("type") FolderNodeType type, BindingResult _result) {
		
		if (_result.hasErrors()) {			
			def model = new ModelAndView(FORM_PAGE)	
			model.addObject "type", type
			return model
		}
		
		type = _result.model.get('type')
				
		gate.dispatch(new CreateFolderNodeTypeCommand(type))
		
		return new ModelAndView("redirect:/ui/admin/folder-node-types")	
	}
	
	@RequestMapping('/ui/admin/folder-node-types/edit/{typeID}') 
	@PreAuthorize("@userAuthorization.hasAdminAccess()")
	public ModelAndView getEditPage(@PathVariable String typeID) {
		
		def type = folderNodeTypeDAO.findOne(typeID)
				
		if (type == null) {
			return new ModelAndView("redirect:/ui/admin/folder-node-types/add")
		}
		
		def model = new ModelAndView(FORM_PAGE)	
		model.addObject "type", type
		
		return model	
	}
	
	@RequestMapping(value = '/ui/admin/folder-node-types/edit/{typeID}', method = RequestMethod.POST) 
	@PreAuthorize("@userAuthorization.hasAdminAccess()")
	public ModelAndView editFolderType(@Valid @ModelAttribute("type") FolderNodeType type, BindingResult _result) {
		
		if (_result.hasErrors()) {			
			def model = new ModelAndView(FORM_PAGE)	
			model.addObject "type", type
			return model
		}
		
		type = _result.model.get('type')

		gate.dispatch(new EditFolderNodeTypeCommand(type))
		
		return new ModelAndView("redirect:/ui/admin/folder-node-types")	
	}
	
}
