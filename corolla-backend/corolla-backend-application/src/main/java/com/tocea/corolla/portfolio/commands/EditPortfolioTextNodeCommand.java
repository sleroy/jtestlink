package com.tocea.corolla.portfolio.commands;

import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class EditPortfolioTextNodeCommand {

	private Integer nodeID;
	
	private String text;
	
	public EditPortfolioTextNodeCommand() {
		super();
	}
	
	public EditPortfolioTextNodeCommand(Integer nodeID, String text) {
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
