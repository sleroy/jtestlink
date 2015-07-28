package com.tocea.corolla.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.trees.commands.DeleteFolderNodeTypeCommand;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.trees.exceptions.MissingFolderNodeTypeInformationException;

@CommandHandler
@Transactional
public class DeleteFolderNodeTypeCommandHandler implements ICommandHandler<DeleteFolderNodeTypeCommand, FolderNodeType> {

	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Override
	public FolderNodeType handle(@Valid DeleteFolderNodeTypeCommand command) {
		
		String typeID = command.getTypeID();
		
		if (StringUtils.isEmpty(typeID)) {
			throw new MissingFolderNodeTypeInformationException("No ID found.");
		}
		
		FolderNodeType folderNodeType = folderNodeTypeDAO.findOne(typeID);
		
		if (folderNodeType != null) {
			folderNodeTypeDAO.delete(folderNodeType);
		}
		
		return folderNodeType;
	}

}
