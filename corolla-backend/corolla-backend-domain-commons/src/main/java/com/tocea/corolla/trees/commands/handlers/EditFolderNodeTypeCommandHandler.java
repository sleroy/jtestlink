package com.tocea.corolla.trees.commands.handlers;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.tocea.corolla.cqrs.annotations.CommandHandler;
import com.tocea.corolla.cqrs.handler.ICommandHandler;
import com.tocea.corolla.trees.commands.EditFolderNodeTypeCommand;
import com.tocea.corolla.trees.dao.IFolderNodeTypeDAO;
import com.tocea.corolla.trees.domain.FolderNodeType;
import com.tocea.corolla.trees.exceptions.InvalidFolderNodeTypeInformationException;
import com.tocea.corolla.trees.exceptions.MissingFolderNodeTypeInformationException;

@CommandHandler
@Transactional
public class EditFolderNodeTypeCommandHandler implements ICommandHandler<EditFolderNodeTypeCommand, FolderNodeType> {

	@Autowired
	private IFolderNodeTypeDAO folderNodeTypeDAO;
	
	@Override
	public FolderNodeType handle(@Valid EditFolderNodeTypeCommand command) {
		
		FolderNodeType type = command.getFolderNodeType();
		
		if (type == null) {
			throw new MissingFolderNodeTypeInformationException("No data found to edit a folder node type");
		}
		
		if (StringUtils.isEmpty(type.getId())) {
			throw new InvalidFolderNodeTypeInformationException("No ID found on this folder node type");
		}
		
		folderNodeTypeDAO.save(type);
		
		return type;
	}

}
