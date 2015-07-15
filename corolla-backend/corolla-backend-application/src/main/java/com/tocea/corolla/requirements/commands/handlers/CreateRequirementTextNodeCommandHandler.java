package com.tocea.corolla.requirements.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.commands.CreateRequirementTextNodeCommand;
import com.tocea.corolla.requirements.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.domain.RequirementsTree;
import com.tocea.corolla.requirements.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.requirements.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.trees.commands.CreateTreeNodeCommand;
import com.tocea.corolla.trees.domain.TextNode;
import com.tocea.corolla.trees.domain.TreeNode;

@CommandHandler
@Transactional
public class CreateRequirementTextNodeCommandHandler implements ICommandHandler<CreateRequirementTextNodeCommand, RequirementsTree> {

	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private Gate gate;
	
	@Override
	public RequirementsTree handle(@Valid CreateRequirementTextNodeCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to retrieve the project branch");
		}
		
		String text = command.getText();
		
		if (StringUtils.isEmpty(text)) {
			throw new InvalidRequirementsTreeInformationException("The text of a text node cannot be empty");
		}
		
		Integer parentID = command.getParentID();
		
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		TreeNode node = new TextNode(text);
		
		tree = (RequirementsTree) gate.dispatch(new CreateTreeNodeCommand(tree, node, parentID));
		
		requirementsTreeDAO.save(tree);
		
		return tree;
		
	}

}
