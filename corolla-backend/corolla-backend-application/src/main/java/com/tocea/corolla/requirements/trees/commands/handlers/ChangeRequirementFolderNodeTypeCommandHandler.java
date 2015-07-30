package com.tocea.corolla.requirements.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.products.domain.ProjectBranch;
import com.tocea.corolla.products.exceptions.MissingProjectBranchInformationException;
import com.tocea.corolla.requirements.trees.commands.ChangeRequirementFolderNodeTypeCommand;
import com.tocea.corolla.requirements.trees.dao.IRequirementsTreeDAO;
import com.tocea.corolla.requirements.trees.domain.RequirementsTree;
import com.tocea.corolla.requirements.trees.exceptions.RequirementsTreeNotFoundException;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNode;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.trees.domain.TreeNode;
import com.tocea.corolla.trees.exceptions.FolderNodeTypeNotFoundException;
import com.tocea.corolla.trees.exceptions.InvalidTreeNodeInformationException;
import com.tocea.corolla.trees.exceptions.MissingTreeNodeInformationException;
import com.tocea.corolla.trees.predicates.FindNodeByIDPredicate;
import com.tocea.corolla.trees.services.ITreeManagementService;
import com.tocea.corolla.trees.utils.TreeNodeUtils;

@CommandHandler
@Transactional
public class ChangeRequirementFolderNodeTypeCommandHandler implements ICommandHandler<ChangeRequirementFolderNodeTypeCommand, FolderNode> {

	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Autowired
	private IRequirementsTreeDAO requirementsTreeDAO;
	
	@Autowired
	private ITreeManagementService treeManagementService;
	
	@Override
	public FolderNode handle(@Valid ChangeRequirementFolderNodeTypeCommand command) {

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
		
		if (!TreeNodeUtils.isFolderNode(node)) {
			throw new InvalidTreeNodeInformationException("This is not a folder node");
		}
		
		String typeID = command.getTypeID();
		
		FolderNodeType folderNodeType = folderNodeTypeDAO.findOne(typeID);
		
		if (folderNodeType == null) {
			throw new FolderNodeTypeNotFoundException(typeID);
		}
		
		((FolderNode)node).setTypeID(typeID);;
		
		requirementsTreeDAO.save(tree);
		
		return (FolderNode) node;
	}

}
