package com.tocea.corolla.products.rest;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.DeleteApplicationCommand;
import com.tocea.corolla.products.dao.IApplicationDAO;
import com.tocea.corolla.products.domain.Application;
import com.tocea.corolla.products.exceptions.InvalidProductException;
import com.tocea.corolla.users.domain.Permission;

@RestController()
@RequestMapping("/rest/applications")
@Secured(Permission.REST)
@Transactional
public class ApplicationRestController {

	@Autowired
	private IApplicationDAO applicationDAO;
	
	@Autowired
	private Gate gate;

	@Secured({ Permission.ADMIN, Permission.ADMIN_ROLES })
	@RequestMapping(value = "/delete/{key}", method = RequestMethod.GET)
	public void deleteUser(@PathVariable final String key) {
		
		Application application = applicationDAO.findApplicationByKey(key);
		
		if (application == null) {
			throw new InvalidProductException();
		}

		gate.dispatch(new DeleteApplicationCommand(application.getId()));

	}
	
}
