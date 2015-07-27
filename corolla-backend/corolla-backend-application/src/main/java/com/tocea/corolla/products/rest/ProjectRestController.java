package com.tocea.corolla.products.rest;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.users.domain.Permission;

@RestController
@RequestMapping("/rest/projects")
public class ProjectRestController {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private Gate gate;
	
	@RequestMapping(value = "/all")
	@Secured({ Permission.REST })
	public Collection<Project> findAll() {
		
		return projectDAO.findAll();
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	@Secured({ Permission.REST })
	public Collection<Project> findAll(@RequestBody List<String> ids) {
		
		return (Collection<Project>) projectDAO.findAll(ids);
	}
	
}
