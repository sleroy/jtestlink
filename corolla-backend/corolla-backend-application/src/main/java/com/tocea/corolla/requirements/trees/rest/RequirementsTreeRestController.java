package com.tocea.corolla.requirements.trees.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException;
import com.tocea.corolla.products.exceptions.ProjectNotFoundException;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.utils.RequirementsTreeUtils;
import com.tocea.corolla.requirements.trees.visitors.RequirementsJsTreeVisitor;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.dto.JsTreeNodeDTO;
import com.tocea.corolla.users.domain.Permission;

@RestController
@RequestMapping("/rest/requirements/tree")
public class RequirementsTreeRestController {

	@Autowired
	private IProjectDAO projectDAO;
	
	@Autowired
	private IProjectBranchDAO projectBranchDAO;
	
	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private IRequirementDAO requirementDAO;
	
	@RequestMapping(value = "/jstree/{projectKey}/{branchName}")
	@Secured({ Permission.REST })
	public Collection<JsTreeNodeDTO> getJsTree(@PathVariable String projectKey, @PathVariable String branchName) {
		
		Project project = projectDAO.findByKey(projectKey);
		
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		
		ProjectBranch branch = projectBranchDAO.findByNameAndProjectId(branchName, project.getId());
		
		if (branch == null) {
			throw new ProjectBranchNotFoundException();
		}
		
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		Collection<String> ids = RequirementsTreeUtils.getRequirementsIDs(tree.getNodes());
		
		Collection<Requirement> requirements = Lists.newArrayList(requirementDAO.findAll(ids));
		
		Collection<JsTreeNodeDTO> nodes = Lists.newArrayList();
		
		for(TreeNode node : tree.getNodes()) {
			
			RequirementsJsTreeVisitor visitor = new RequirementsJsTreeVisitor(requirements);
			node.accept(visitor);
			nodes.add(visitor.getResult());
			
		}

		return nodes;		
	}
	
}