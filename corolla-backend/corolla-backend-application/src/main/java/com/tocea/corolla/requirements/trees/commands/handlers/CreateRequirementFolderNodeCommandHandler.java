package com.tocea.corolla.requirements.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.trees.commands.CreateRequirementFolderNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.InvalidRequirementsTreeInformationException;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.services.ITreeManagementService;

@CommandHandler
@Transactional
public class CreateRequirementFolderNodeCommandHandler implements ICommandHandler<CreateRequirementFolderNodeCommand, FolderNode> {

	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public FolderNode handle(@Valid CreateRequirementFolderNodeCommand command) {
		
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
		
		String typeID = command.getTypeID();
		
		FolderNode node = new FolderNode(text, typeID);
		
		treeManagementService.insertNode(tree, parentID, node);
		
		requirementsTreeDAO.save(tree);
		
		return node;
		
	}

}
