package com.tocea.corolla.core.plugins.api;

import com.tocea.corolla.core.plugins.model.PluginDefinition;

/**
 * This interface is a wrapper above a plugin to provide its information.
 *
 * @author sleroy
 *        
 */
public interface IPlugin {
	/**
	 * Returns the plugin definition.
	 *
	 * @return the plugin definition.
	 */
	PluginDefinition getDefinition();
	
	/**
	 * Returns the plugin classloader.
	 *
	 * @return the plugin classloader.
	 */
	ClassLoader getPluginClassLoader();
	
}
