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
	private List<String>	jsResources	= new ArrayList<>();
	private List<String>	cssResources	= new ArrayList<>();

	/**
	 * Associates a new JS resource to the plugin.
	 *
	 * @param _path
	 */
	public void addJSResource(final String _path) {
		jsResources.add(_path);
	}

	public List<String> getCssResources() {
		return cssResources;
	}

	public List<String> getJsResources() {
		return jsResources;
	}

	public void setCssResources(final List<String> _cssResources) {
		cssResources = _cssResources;
	}

	public void setJsResources(final List<String> _resources) {
		jsResources = _resources;
	}

	@Override
	public String toString() {
		return "PluginResources [jsResources=" + jsResources + ", cssResources=" + cssResources + "]";
	}
}
