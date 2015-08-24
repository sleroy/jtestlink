package com.tocea.corolla.core.plugins.api;

import java.io.Closeable;

import com.tocea.corolla.core.plugins.model.PluginDefinition;

/**
 * This interface is a wrapper above a plugin to provide its information.
 *
 * @author sleroy
 *
 */
public interface IPlugin extends Closeable {
	/**
	 * Returns the plugin definition.
	 *
	 * @return the plugin definition.
	 */
	PluginDefinition getDefinition();

	

	/**
	 * Initializes the plugin.
	 */
	void initialize();

}
