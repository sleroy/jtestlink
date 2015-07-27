package com.tocea.corolla.requirements.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.gate.Gate;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.trees.commands.EditRequirementTextNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.trees.commands.EditTextNodeCommand;

@CommandHandler
@Transactional
public class EditRequirementTextNodeCommandHandler implements ICommandHandler<EditRequirementTextNodeCommand, RequirementsTree> {

	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private Gate gate;
	
	@Override
	public RequirementsTree handle(@Valid EditRequirementTextNodeCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to retrieve the project branch");
		}
			
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		Integer nodeID = command.getNodeID();
		String text = command.getText();
		
		tree = gate.dispatch(new EditTextNodeCommand(tree, nodeID, text));
		
		requirementsTreeDAO.save(tree);
		
		return tree;
	}

}
