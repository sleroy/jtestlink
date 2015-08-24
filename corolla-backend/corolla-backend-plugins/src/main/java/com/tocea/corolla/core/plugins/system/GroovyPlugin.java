package com.tocea.corolla.core.plugins.system;

import java.io.IOException;

import org.springframework.core.io.Resource;

import com.tocea.corolla.core.plugins.api.IPlugin;
import com.tocea.corolla.core.plugins.model.PluginDefinition;

public class GroovyPlugin implements IPlugin {
	
	private final PluginDefinition pluginDefinition;
	private final Resource[]		groovyResources;
	
	public GroovyPlugin(final PluginDefinition _pluginDefinition, final Resource[] _groovyResources) {
		pluginDefinition = _pluginDefinition;
		groovyResources = _groovyResources;
	}
	
	@Override
	public void close() throws IOException {
		//
	}
	
	@Override
	public PluginDefinition getDefinition() {
		return pluginDefinition;
	}
	
	@Override
	public void initialize() {
		// Nothing to do
	}
	
}
