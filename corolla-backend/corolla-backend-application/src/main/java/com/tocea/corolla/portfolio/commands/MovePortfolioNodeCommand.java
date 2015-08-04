package com.tocea.corolla.portfolio.commands;

import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class MovePortfolioNodeCommand {

	private Integer nodeID;
	
	private Integer newParentID;
	
	public MovePortfolioNodeCommand() {
		super();
	}
	
	public MovePortfolioNodeCommand(Integer nodeID, Integer newParentID) {
		super();
		setNodeID(nodeID);
		setNewParentID(newParentID);
	}

	public Integer getNewParentID() {
		return newParentID;
	}

	public void setNewParentID(Integer newParentID) {
		this.newParentID = newParentID;
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}
	
}
