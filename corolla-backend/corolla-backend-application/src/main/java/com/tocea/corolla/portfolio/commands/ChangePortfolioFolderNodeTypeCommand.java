package com.tocea.corolla.portfolio.commands;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class ChangePortfolioFolderNodeTypeCommand {

	@NotNull
	private Integer nodeID;
	
	@NotBlank
	private String typeID;
	
	public ChangePortfolioFolderNodeTypeCommand() {
		super();
	}
	
	public ChangePortfolioFolderNodeTypeCommand(Integer nodeID, String typeID) {
		super();
		setNodeID(nodeID);
		setTypeID(typeID);
	}

	public Integer getNodeID() {
		return nodeID;
	}

	public void setNodeID(Integer nodeID) {
		this.nodeID = nodeID;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	
}
