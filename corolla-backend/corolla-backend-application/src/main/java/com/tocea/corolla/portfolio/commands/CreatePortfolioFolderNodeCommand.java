package com.tocea.corolla.portfolio.commands;

import com.tocea.corolla.cqrs.annotations.CommandOptions;
import com.tocea.corolla.trees.domain.FolderNodeType;

@CommandOptions
public class CreatePortfolioFolderNodeCommand {

	private String text;
	
	private Integer parentID;
	
	private String typeID;
	
	public CreatePortfolioFolderNodeCommand() {
		super();
	}
	
	public CreatePortfolioFolderNodeCommand(String text, String typeID, Integer parentID) {
		super();
		setText(text);
		setParentID(parentID);
		setTypeID(typeID);
	}
	
	public CreatePortfolioFolderNodeCommand(String text, FolderNodeType type, Integer parentID) {
		this(text, type != null ? type.getId() : null, parentID);
	}

	public Integer getParentID() {
		return parentID;
	}

	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	
}
