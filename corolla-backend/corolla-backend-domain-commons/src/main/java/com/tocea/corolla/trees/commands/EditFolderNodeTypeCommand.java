package com.tocea.corolla.trees.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.trees.domain.FolderNodeType;

@CommandOptions
public class EditFolderNodeTypeCommand {

	@NotNull
	private FolderNodeType folderNodeType;

	public EditFolderNodeTypeCommand() {
		super();
	}
	
	public EditFolderNodeTypeCommand(FolderNodeType folderNodeType) {
		super();
		setFolderNodeType(folderNodeType);
	}
	
	public FolderNodeType getFolderNodeType() {
		return folderNodeType;
	}

	public void setFolderNodeType(FolderNodeType folderNodeType) {
		this.folderNodeType = folderNodeType;
	}
	
}
