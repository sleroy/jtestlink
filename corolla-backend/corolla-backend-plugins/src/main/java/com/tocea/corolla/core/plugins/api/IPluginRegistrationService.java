package com.tocea.corolla.core.plugins.api;

import com.tocea.corolla.core.plugins.model.PluginDefinition;

public interface IPluginRegistrationService {
	/**
	 * This method needs to be called to register a plugin inside Corolla;F
	 *
	 * @param _pluginDefinition
	 *            the plugin definition
	 * @param _classLoader
	 *            its class loader.
	 */
	void registerPlugin(PluginDefinition _pluginDefinition, ClassLoader _classLoader);
}
