package com.tocea.corolla.trees.commands;

import javax.validation.constraints.NotNull;

import com.tocea.corolla.cqrs.annotations.Command;
import com.tocea.corolla.trees.domain.FolderNodeType;

@Command
public class CreateFolderNodeTypeCommand {

	@NotNull
	private FolderNodeType nodeType;
	
	public CreateFolderNodeTypeCommand() {
		super();
	}
	
	public CreateFolderNodeTypeCommand(FolderNodeType nodeType) {
		super();
		setNodeType(nodeType);
	}

	public FolderNodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(FolderNodeType nodeType) {
		this.nodeType = nodeType;
	}
	
}
