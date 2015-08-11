package com.tocea.corolla.products.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tocea.corolla.products.dao.IProjectCategoryDAO;
import com.tocea.corolla.products.domain.ProjectCategory;

@RestController
@RequestMapping("/rest/projects/categories")
public class ProjectCategoryRestController {

	@Autowired
	private IProjectCategoryDAO categoryDAO;
	
	@RequestMapping("/all")
	@PreAuthorize("isAuthenticated()")
	public Collection<ProjectCategory> findAll() {
		
		return categoryDAO.findAll();
	}
	
}
