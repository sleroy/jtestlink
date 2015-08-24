package com.tocea.corolla.core.plugins.system;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tocea.corolla.core.plugins.api.IPlugin;

/**
 * This class stores all the plugins.
 *
 * @author sleroy
 *
 */
public class PluginLibrary {
	private final Map<String, IPlugin> plugins = new HashMap<>();

	public void addPlugin(final IPlugin _plugin) {
		if (hasPlugin(_plugin.getDefinition().getKey())) {
			throw new IllegalArgumentException("Plugin ");
		}
	}

	/**
	 * Returns the list of plugins.
	 *
	 * @return the list of plugins.
	 */
	public Collection<IPlugin> getPlugins() {
		return Collections.unmodifiableCollection(plugins.values());
	}

	/**
	 * Tests if a plugin is already existing
	 *
	 * @param _pluginKey
	 *            the plugin key.
	 * @return true if the plugin is already present;
	 */
	public boolean hasPlugin(final String _pluginKey) {
		return plugins.containsKey(_pluginKey);
	}
}
