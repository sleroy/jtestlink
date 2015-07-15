package com.tocea.corolla.requirements.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.commands.DeleteRequirementCommand;
import com.tocea.corolla.requirements.commands.RemoveRequirementTreeNodeCommand;
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.RequirementsTree;
import com.tocea.corolla.requirements.exceptions.MissingRequirementInformationException;
import com.tocea.corolla.requirements.exceptions.RequirementTreeNodeNotFoundException;
import com.tocea.corolla.requirements.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.requirements.utils.RequirementsTreeUtils;
import com.tocea.corolla.trees.commands.RemoveTreeNodeCommand;
import com.tocea.corolla.trees.domain.TreeNode;

@CommandHandler
@Transactional
public class RemoveRequirementTreeNodeCommandHandler implements ICommandHandler<RemoveRequirementTreeNodeCommand, RequirementsTree> {

	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private Gate gate;
	
	@Override
	public RequirementsTree handle(@Valid RemoveRequirementTreeNodeCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No project branch found");
		}
		
		String requirementID = command.getRequirementID();
		
		if (StringUtils.isEmpty(requirementID)) {
			throw new MissingRequirementInformationException("No requirement ID found");
		}
		
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		TreeNode node = RequirementsTreeUtils.getNodeByRequirementId(requirementID, tree.getNodes());
		
		if (node == null) {
			throw new RequirementTreeNodeNotFoundException();
		}
		
		gate.dispatch(new DeleteRequirementCommand(requirementID));
		
		tree = gate.dispatch(new RemoveTreeNodeCommand(tree, node.getId()));
		
		requirementsTreeDAO.save(tree);
		
		return tree;
	}
	
}