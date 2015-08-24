package com.tocea.corolla.core.plugins.model;

import java.util.ArrayList;
import java.util.List;

public class SideMenuItem {
	private String header;
	
	private List<SideMenuAction> actions = new ArrayList<>();
	
	public void addAction(final SideMenuAction _action) {
		actions.add(_action);
	}
	
	public List<SideMenuAction> getActions() {
		return actions;
	}
	
	public String getHeader() {
		return header;
	}
	
	public void setActions(final List<SideMenuAction> _actions) {
		actions = _actions;
	}
	
	public void setHeader(final String _header) {
		header = _header;
	}

	
}
