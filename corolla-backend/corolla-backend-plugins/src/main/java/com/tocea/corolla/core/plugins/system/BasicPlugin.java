package com.tocea.corolla.core.plugins.system;

import java.io.IOException;

import com.tocea.corolla.core.plugins.api.IPlugin;
import com.tocea.corolla.core.plugins.model.PluginDefinition;

public class BasicPlugin implements IPlugin {

	private final PluginDefinition	pluginDefinition;

	public BasicPlugin(final PluginDefinition _pluginDefinition) {
		pluginDefinition = _pluginDefinition;
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
