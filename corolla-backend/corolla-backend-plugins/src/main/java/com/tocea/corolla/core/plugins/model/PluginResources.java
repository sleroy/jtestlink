package com.tocea.corolla.core.plugins.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the resources required by the plugin to works properly.
 * 
 * @author sleroy
 *        
 */
public class PluginResources {
	private List<String> jsResources = new ArrayList<>();
	
	public List<String> getJsResources() {
		return jsResources;
	}

	public void setJsResources(final List<String> _resources) {
		jsResources = _resources;
	}
	
	@Override
	public String toString() {
		return "PluginResources [resources=" + jsResources + "]";
	}
}
