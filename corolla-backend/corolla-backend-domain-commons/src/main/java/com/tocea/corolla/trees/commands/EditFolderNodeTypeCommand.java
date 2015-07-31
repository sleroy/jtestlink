package com.tocea.corolla.trees.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.trees.domain.FolderNodeType;

@Command
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
