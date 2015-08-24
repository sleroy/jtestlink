package com.tocea.corolla.core.plugins.api;

import com.tocea.corolla.core.plugins.model.PluginDefinition;

public interface IPluginRegistrationService {
	/**
	 * This method register an plugin integrator. A plugin integrator is
	 * notified when the plugin container initializes the plugin and destroy it
	 * to handle its own configuration.
	 *
	 * @param _pluginIntegrator
	 */
	void registerIntegrator(IPluginIntegrator _pluginIntegrator);
	
	/**
	 * Registers a plugin
	 *
	 * @param _plugin
	 *            the plugin.
	 */
	void registerPlugin(IPlugin _plugin);
	
	/**
	 * This method needs to be called to register a plugin inside Corolla;
	 *
	 * @param _pluginDefinition
	 *            the plugin definition
	 * @param _classLoader
	 *            its class loader.
	 */
	void registerPlugin(PluginDefinition _pluginDefinition);
}
