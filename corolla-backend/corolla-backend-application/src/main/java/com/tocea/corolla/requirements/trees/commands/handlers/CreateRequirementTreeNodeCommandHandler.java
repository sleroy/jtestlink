package com.tocea.corolla.requirements.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.trees.commands.CreateRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementNode;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementTreeNodeAlreadyExistException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.requirements.trees.predicates.FindNodeByRequirementIDPredicate;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.services.ITreeManagementService;

@CommandHandler
@Transactional
public class CreateRequirementTreeNodeCommandHandler implements ICommandHandler<CreateRequirementTreeNodeCommand, RequirementsTree> {
	
	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public RequirementsTree handle(@Valid CreateRequirementTreeNodeCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to retrieve the project branch");
		}
		
		String requirementId = command.getRequirementId();
		
		if (StringUtils.isEmpty(requirementId)) {
			throw new InvalidRequirementsTreeInformationException("Cannot create a requirement node without a requirement ID");
		}
		
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		Integer parentId = command.getParentId();
		
		TreeNode sameNode = treeManagementService.findNode(tree, new FindNodeByRequirementIDPredicate(requirementId));
		
		if (sameNode != null) {
			throw new RequirementTreeNodeAlreadyExistException();
		}
		
		RequirementNode newNode = new RequirementNode();
		newNode.setRequirementId(requirementId);		
		
		treeManagementService.insertNode(tree, parentId, newNode);
		
		requirementsTreeDAO.save(tree);
		
		return tree;	
	}
	
}