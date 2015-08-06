package com.tocea.corolla.portfolio.commands;

import com.tocea.corolla.cqrs.annotations.CommandOptions;

@CommandOptions
public class RemovePortfolioNodeCommand {

	private Integer nodeID;
	
	public RemovePortfolioNodeCommand() {
		super();
	}
	
	public RemovePortfolioNodeCommand(Integer nodeID) {
		super();
		setNodeID(nodeID);
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}
	
}
