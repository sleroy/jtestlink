package com.tocea.corolla.portfolio.commands;

import com.tocea.corolla.cqrs.annotations.CommandOptions;

@CommandOptions
public class EditPortfolioFolderNodeCommand {

	private Integer nodeID;
	
	private String text;
	
	public EditPortfolioFolderNodeCommand() {
		super();
	}
	
	public EditPortfolioFolderNodeCommand(Integer nodeID, String text) {
		super();
		setNodeID(nodeID);
		setText(text);
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
