package com.tocea.corolla.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.trees.commands.CreateFolderNodeTypeCommand;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.trees.exceptions.InvalidFolderNodeTypeInformationException;
import com.tocea.corolla.trees.exceptions.MissingFolderNodeTypeInformationException;

@CommandHandler
@Transactional
public class CreateFolderNodeTypeCommandHandler implements ICommandHandler<CreateFolderNodeTypeCommand, FolderNodeType> {

	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Override
	public FolderNodeType handle(@Valid CreateFolderNodeTypeCommand command) {
		
		FolderNodeType nodeType = command.getNodeType();
		
		if (nodeType == null) {
			throw new MissingFolderNodeTypeInformationException("No folder node type found");
		}
		
		if (StringUtils.isNotEmpty(nodeType.getId())) {
			throw new InvalidFolderNodeTypeInformationException("No ID expected");
		}
		
		folderNodeTypeDAO.save(nodeType);
		
		return nodeType;
	}

}
