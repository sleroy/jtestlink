package com.tocea.corolla.core.plugins.api;

import java.util.List;

public interface IPluginContainer {
	/**
	 * Returns the list of plugins.
	 *
	 * @return the list of plugins declared inside Corolla;
	 */
	List<IPlugin> getPlugins();
}
