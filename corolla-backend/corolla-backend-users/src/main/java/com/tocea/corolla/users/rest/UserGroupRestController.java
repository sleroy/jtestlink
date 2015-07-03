package com.tocea.corolla.users.rest;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.users.commands.DeleteUserGroupCommand;
import com.tocea.corolla.users.domain.Permission;

@RestController()
@RequestMapping("/rest/groups")
@Secured(Permission.REST)
@Transactional
public class UserGroupRestController {

	@Autowired
	private Gate gate;
	
	@Secured({ Permission.ADMIN, Permission.ADMIN_USER_GROUPS })
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public void deleteUserGroup(@PathVariable final String id) {
		
		this.gate.dispatch(new DeleteUserGroupCommand(id));
		
	}
	
}
