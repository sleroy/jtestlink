package com.tocea.corolla.portfolio.commands;

import com.tocea.corolla.cqrs.annotations.Command;

@Command
public class CreatePortfolioTextNodeCommand {

	private String text;
	
	private Integer parentID;
	
	public CreatePortfolioTextNodeCommand() {
		super();
	}
	
	public CreatePortfolioTextNodeCommand(String text, Integer parentID) {
		super();
		setText(text);
		setParentID(parentID);
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
	
}
