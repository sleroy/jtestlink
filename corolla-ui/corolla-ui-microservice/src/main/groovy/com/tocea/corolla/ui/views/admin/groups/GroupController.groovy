package com.tocea.corolla.ui.views.admin.groups

import javax.validation.Valid;

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private Gate gate;
	
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
	public ModelAndView addGroup(@Valid @ModelAttribute("group") UserGroup group, BindingResult _result) {
		
		group = _result.model.get("group")
				
		if (_result.hasErrors()) {
			
			def model = new ModelAndView(EDIT_PAGE)
			model.addObject "group", group
			model.addObject "users", userDTODAO.getUsersWithRoleList()
			
			return model
			
		}
		
		this.gate.dispatch new CreateUserGroupCommand(group)
		
		return new ModelAndView("redirect:/ui/admin/groups")
		
	}
	
	@RequestMapping("/ui/admin/groups/edit/{id}")
	public ModelAndView getEditPage(@PathVariable id) {
		
		UserGroup group = groupDAO.findOne(id)
		def users = userDTODAO.getUsersWithRoleList()
		
		def model = new ModelAndView(EDIT_PAGE)
		model.addObject "group", group != null ? group : new UserGroup()
		model.addObject "users", users
		model.addObject "users_in_group", users.findAll { group.userIds ? group.userIds.contains(it.login) : false }
		
		return model
		
	}
	
	@RequestMapping(value = "/ui/admin/groups/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editGroup(@PathVariable id, @Valid @ModelAttribute("group") UserGroup group, BindingResult _result) {
		
		group = _result.model.get("group")
		log.info("{}", group.userIds)
		
		if (id != group.id) {			
			return new ModelAndView("redirect:/ui/admin/groups/edit/"+id)
		}
					
		if (_result.hasErrors()) {
			
			def model = new ModelAndView(EDIT_PAGE)
			model.addObject "group", group
			model.addObject "users", userDTODAO.getUsersWithRoleList()
			
			return model
			
		}
		
		this.gate.dispatch new EditUserGroupCommand(group)
		
		return new ModelAndView("redirect:/ui/admin/groups")
		
	}
	
}