package com.tocea.corolla.products.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.products.dao.IProjectStatusDAO;
import com.tocea.corolla.products.domain.ProjectStatus;

@RestController
@RequestMapping("/rest/projects/status")
public class ProjectStatusRestController {

	@Autowired
	private IProjectStatusDAO statusDAO;
	
	@RequestMapping("/all")
	@PreAuthorize("isAuthenticated()")
	public Collection<ProjectStatus> findAll() {
		
		return statusDAO.findAll();
	}
	
}