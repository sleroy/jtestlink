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
package com.tocea.corolla.requirements.trees.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.products.dao.IProjectBranchDAO;
import com.tocea.corolla.products.dao.IProjectDAO;
import com.tocea.corolla.products.domain.Project;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.ProjectBranchNotFoundException;
import com.tocea.corolla.products.exceptions.ProjectNotFoundException;
import com.tocea.corolla.requirements.dao.IRequirementDAO;
import com.tocea.corolla.requirements.domain.Requirement;
import com.tocea.corolla.requirements.trees.commands.ChangeRequirementFolderNodeTypeCommand;
import com.tocea.corolla.requirements.trees.commands.CreateRequirementFolderNodeCommand;
import com.tocea.corolla.requirements.trees.commands.EditRequirementFolderNodeCommand;
import com.tocea.corolla.requirements.trees.commands.MoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.commands.RemoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.utils.RequirementsTreeUtils;
import com.tocea.corolla.requirements.trees.visitors.RequirementsJsTreeVisitor;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.domain.FolderNodeType;
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
	
	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Autowired
	private Gate gate;
	
	@RequestMapping(value = "{projectKey}/{branchName}/")
	@PreAuthorize("isAuthenticated()")
	public RequirementsTree getTree(@PathVariable String projectKey, @PathVariable String branchName) {
		
		Project project = findProjectOrFail(projectKey);		
		ProjectBranch branch = findProjectBranchOrFail(branchName, project);

		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		return tree;
	}
	
	@RequestMapping(value = "{projectKey}/{branchName}/jstree")
	@PreAuthorize("isAuthenticated()")
	public Collection<JsTreeNodeDTO> getJsTree(@PathVariable String projectKey, @PathVariable String branchName) {
		
		Project project = findProjectOrFail(projectKey);		
		ProjectBranch branch = findProjectBranchOrFail(branchName, project);

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
	
	@RequestMapping(value = "/{projectKey}/{branchName}/move/{fromID}/{toID}")
	@Secured({ Permission.PROJECT_WRITE })
	public RequirementsTree moveNode(@PathVariable String projectKey, @PathVariable String branchName, @PathVariable Integer fromID, @PathVariable Integer toID) {
		
		Project project = findProjectOrFail(projectKey);		
		ProjectBranch branch = findProjectBranchOrFail(branchName, project);
		
		return gate.dispatch(new MoveRequirementTreeNodeCommand(branch, fromID, toID != 0 ? toID : null));
	}
	
	@RequestMapping(value = "/{projectKey}/{branchName}/folders/add/{parentID}/{folderNodeTypeID}", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.PROJECT_WRITE })
	public FolderNode addTextNode(@PathVariable String projectKey, @PathVariable String branchName, @PathVariable Integer parentID, @PathVariable String folderNodeTypeID, @RequestBody String text) {
		
		Project project = findProjectOrFail(projectKey);		
		ProjectBranch branch = findProjectBranchOrFail(branchName, project);
		
		FolderNodeType folderNodeType = folderNodeTypeDAO.findOne(folderNodeTypeID);
		
		return gate.dispatch(new CreateRequirementFolderNodeCommand(branch, parentID, text, folderNodeType));
	}
	
	@RequestMapping(value = "/{projectKey}/{branchName}/folders/add/{folderNodeTypeID}", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.PROJECT_WRITE })
	public FolderNode addTextNode(@PathVariable String projectKey, @PathVariable String branchName, @PathVariable String folderNodeTypeID, @RequestBody String text) {
		
		return addTextNode(projectKey, branchName, null, folderNodeTypeID, text);
	}
	
	@RequestMapping(value = "/{projectKey}/{branchName}/remove/{nodeID}")
	@Secured({ Permission.PROJECT_WRITE })
	public RequirementsTree removeNode(@PathVariable String projectKey, @PathVariable String branchName, @PathVariable Integer nodeID) {
		
		Project project = findProjectOrFail(projectKey);		
		ProjectBranch branch = findProjectBranchOrFail(branchName, project);
		
		return gate.dispatch(new RemoveRequirementTreeNodeCommand(branch, nodeID));
	}
	
	@RequestMapping(value = "/{projectKey}/{branchName}/folders/edit/{nodeID}", method = RequestMethod.POST, consumes = "text/plain")
	@Secured({ Permission.PROJECT_WRITE })
	public RequirementsTree editTextNode(@PathVariable String projectKey, @PathVariable String branchName, @PathVariable Integer nodeID, @RequestBody String text) {
		
		Project project = findProjectOrFail(projectKey);		
		ProjectBranch branch = findProjectBranchOrFail(branchName, project);
		
		return gate.dispatch(new EditRequirementFolderNodeCommand(branch, nodeID, text));
	}
	
	@RequestMapping(value = "/{projectKey}/{branchName}/folders/edit/type/{nodeID}/{typeID}")
	@Secured({ Permission.PROJECT_WRITE })
	public FolderNode changeFolderNodeType(@PathVariable String projectKey, @PathVariable String branchName, @PathVariable Integer nodeID, @PathVariable String typeID) {
		
		Project project = findProjectOrFail(projectKey);		
		ProjectBranch branch = findProjectBranchOrFail(branchName, project);
		
		return gate.dispatch(new ChangeRequirementFolderNodeTypeCommand(branch, nodeID, typeID));
	}
	
	private Project findProjectOrFail(String projectKey) {
		
		Project project = projectDAO.findByKey(projectKey);
		
		if (project == null) {
			throw new ProjectNotFoundException();
		}
		
		return project;
	}
	
	private ProjectBranch findProjectBranchOrFail(String branchName, Project project) {
		
		ProjectBranch branch = projectBranchDAO.findByNameAndProjectId(branchName, project.getId());
		
		if (branch == null) {
			throw new ProjectBranchNotFoundException();
		}
		
		return branch;
	}
}