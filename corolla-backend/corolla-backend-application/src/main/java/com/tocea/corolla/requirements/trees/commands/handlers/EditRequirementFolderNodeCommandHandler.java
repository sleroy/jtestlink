package com.tocea.corolla.requirements.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.trees.commands.EditRequirementFolderNodeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
@Transactional
public class EditRequirementFolderNodeCommandHandler implements ICommandHandler<EditRequirementFolderNodeCommand, RequirementsTree> {

	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public RequirementsTree handle(@Valid EditRequirementFolderNodeCommand command) {
		
		ProjectBranch branch = command.getBranch();
		
		if (branch == null) {
			throw new MissingProjectBranchInformationException("No data provided to retrieve the project branch");
		}
			
		RequirementsTree tree = requirementsTreeDAO.findByBranchId(branch.getId());
		
		if (tree == null) {
			throw new RequirementsTreeNotFoundException();
		}
		
		Integer nodeID = command.getNodeID();
		
		if (nodeID == null) {
			throw new MissingTreeNodeInformationException("No node ID found");
		}
		
		TreeNode node = treeManagementService.findNode(tree, new FindNodeByIDPredicate(nodeID));
		
		if (node == null) {
			throw new InvalidTreeNodeInformationException("No node found for this ID");
		}
		
		String text = command.getText();
		
		if (!TreeNodeUtils.isFolderNode(node)) {
			throw new InvalidTreeNodeInformationException("This is not a folder node");
		}
		
		((FolderNode)node).setText(text);
		
		requirementsTreeDAO.save(tree);
		
		return tree;
	}

}