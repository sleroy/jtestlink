package com.tocea.corolla.products.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.CommandExecutionException;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.commands.DeleteProjectBranchCommand;
import com.tocea.corolla.products.commands.EditProjectCommand;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException;
import com.tocea.corolla.products.exceptions.ProjectNotFoundException;
import com.tocea.corolla.users.domain.Permission;

@RestController
@RequestMapping("/rest/projects")
public class ProjectRestController {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IProjectBranchDAO branchDAO;
	
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
	
	@RequestMapping(value="/{projectKey}/tags")
	public Collection<String> findTags(@PathVariable String projectKey) {
		
		Project project = projectDAO.findByKey(projectKey);
		
		if (project != null && project.getTags() != null) {			
			return project.getTags();
		}
		
		return Lists.newArrayList();
	}
	
	@RequestMapping(value="/{projectKey}/tags/push", method = RequestMethod.POST)
	@Secured({ Permission.PROJECT_MANAGEMENT })
	public Collection<String> pushTags(@PathVariable String projectKey, @RequestBody String data) throws UnsupportedEncodingException {
		
		Project project = projectDAO.findByKey(projectKey);
		
		if (project != null) {
			
			data = data.replace("tags=", "");
			data = URLDecoder.decode(data, "UTF-8");
			List<String> tags = Lists.newArrayList(data.split(","));
			
			project.setTags(tags);
			
			gate.dispatch(new EditProjectCommand(project));
			
			return project.getTags();
		}

		return Lists.newArrayList();
	}
	
	@RequestMapping(value = "/{projectKey}/branches/{branchName}/delete")
	@Secured({ Permission.PROJECT_MANAGEMENT })
	public void deleteBranch(@PathVariable String projectKey, @PathVariable String branchName) throws Throwable {
		
		Project project = projectDAO.findByKey(projectKey);
		
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		
		ProjectBranch branch = branchDAO.findByNameAndProjectId(branchName, project.getId());
		
		if (branch == null) {
			throw new ProjectBranchNotFoundException();
		}
		
		gate.dispatch(new DeleteProjectBranchCommand(branch));

	}
	
	@ResponseStatus(value=HttpStatus.NOT_ACCEPTABLE)
	@ExceptionHandler({ CommandExecutionException.class })
	public void handlePageNotFoundException() {
		
	}
	
}