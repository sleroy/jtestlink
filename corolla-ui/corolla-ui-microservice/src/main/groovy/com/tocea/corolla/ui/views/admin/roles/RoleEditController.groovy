package com.tocea.corolla.ui.views.admin.roles

import groovy.util.logging.Slf4j;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute

import javax.validation.Valid

import com.tocea.corolla.cqrs.gate.Gate
import com.tocea.corolla.users.commands.CreateRoleCommand;
import com.tocea.corolla.users.commands.EditRoleCommand;
import com.tocea.corolla.users.domain.Permission
import com.tocea.corolla.users.dao.IRoleDAO
import com.tocea.corolla.users.domain.Role

@Secured([
	Permission.ADMIN,
	Permission.ADMIN_ROLES
])
@Slf4j
@Controller
@RequestMapping("/ui/admin/roles")
public class RoleEditController {
	
	static final String ADMIN_ROLES_EDIT = "admin/roles_edit"
	
	@Autowired
	private IRoleDAO roleDAO
	
	@Autowired
	private Gate gate

	@ModelAttribute("sideMenu")
	public String addAccount() {
		return "adminMenu"
	}
	
	@RequestMapping("/add")
	public ModelAndView getAddPage(@ModelAttribute Role role) {
		
		def ModelAndView model = new ModelAndView(ADMIN_ROLES_EDIT)
		model.addObject "role", role
		
		return model
				
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public ModelAndView saveNewRole(@Valid @ModelAttribute("role") Role _role, BindingResult _result) {
		
		if (_result.hasErrors()) {
			def ModelAndView model2 = new ModelAndView(ADMIN_ROLES_EDIT)
			model2.addObject "role", _role
			return model2
		}
		
		CreateRoleCommand command = new CreateRoleCommand(_role)
		gate.dispatch(command)
		
		return new ModelAndView("redirect:/ui/admin/roles")
		
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView getEditPage(@PathVariable Integer id) {
		
		Role role = roleDAO.findOne(id)
				
		if (role == null) {
			log.error("Role not found {}", id)
			return new ModelAndView("redirect:/ui/admin/roles/add")
		}
		
		def model = new ModelAndView(ADMIN_ROLES_EDIT)
		model.addObject "role", role
		
		return model
		
	}
	
	@RequestMapping(value="/edit/{id}", method = RequestMethod.POST)
	public ModelAndView modifyRole(@Valid @ModelAttribute("role") Role _role, BindingResult _result) {
		
		if (_result.hasErrors()) {
			def ModelAndView model2 = new ModelAndView(ADMIN_ROLES_EDIT)
			model2.addObject "role", _role
			return model2
		}
		
		Role role = roleDAO.findOne(_role.getId());
		if (role != null) {
			role.setName(_role.getName());
			role.setNote(_role.getNote());
		}
		
		EditRoleCommand command = new EditRoleCommand(role)
		gate.dispatch(command)
		
		return new ModelAndView("redirect:/ui/admin/roles")
		
	}
	
}