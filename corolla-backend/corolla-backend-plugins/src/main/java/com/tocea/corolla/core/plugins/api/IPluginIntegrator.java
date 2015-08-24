package com.tocea.corolla.core.plugins.api;

public interface IPluginIntegrator {
	
	void pluginDestroyed(IPlugin _plugin);

	void pluginStarted(IPlugin _plugin);
}
