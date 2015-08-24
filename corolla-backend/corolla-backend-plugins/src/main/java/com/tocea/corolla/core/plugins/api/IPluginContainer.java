package com.tocea.corolla.core.plugins.api;

import java.io.Closeable;
import java.util.Collection;

public interface IPluginContainer extends Closeable {
	/**
	 * Returns the list of plugins.
	 *
	 * @return the list of plugins declared inside Corolla;
	 */
	Collection<IPlugin> getPlugins();


}
