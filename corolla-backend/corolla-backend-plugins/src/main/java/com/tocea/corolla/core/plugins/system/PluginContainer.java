package com.tocea.corolla.core.plugins.system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tocea.corolla.core.plugins.api.IPlugin;
import com.tocea.corolla.core.plugins.api.IPluginContainer;
import com.tocea.corolla.core.plugins.api.IPluginIntegrator;
import com.tocea.corolla.core.plugins.api.IPluginRegistrationService;
import com.tocea.corolla.core.plugins.model.PluginDefinition;

@Service
public class PluginContainer implements IPluginRegistrationService, IPluginContainer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PluginContainer.class);

	private final PluginLibrary pluginLibrary = new PluginLibrary();

	private final List<IPluginIntegrator> integrators = new ArrayList<>();

	@PreDestroy
	@Override
	public void close() throws IOException {
		LOGGER.info("Closing pluginLibrary....");
		for (final IPlugin plugin : pluginLibrary.getPlugins()) {
			LOGGER.info("Removing Plugin {}", plugin.getDefinition().getName());
			for (final IPluginIntegrator pluginIntegrator : integrators) {
				pluginIntegrator.pluginDestroyed(plugin);
			}
		}
		for (final IPlugin plugin : pluginLibrary.getPlugins()) {
			LOGGER.info("Destroying Plugin {}", plugin.getDefinition().getName());
			plugin.close();
		}
	}

	@Override
	public Collection<IPlugin> getPlugins() {
		return pluginLibrary.getPlugins();
	}

	@Override
	public void registerIntegrator(final IPluginIntegrator _pluginIntegrator) {
		LOGGER.info("[INTEGRATOR] {} registered", _pluginIntegrator);
		integrators.add(_pluginIntegrator);
	}

	@Override
	public void registerPlugin(final IPlugin _plugin) {
		LOGGER.info("[PLUGIN] {} registered", _plugin.getDefinition().getKey());

		addAndStartPlugin(_plugin);

	}

	@Override
	public void registerPlugin(final PluginDefinition _pluginDefinition) {
		LOGGER.info("[PLUGIN] {} registered", _pluginDefinition.getName());
		LOGGER.info("[PLUGIN] \t {} ", _pluginDefinition.getDescription());
		final BasicPlugin plugin = new BasicPlugin(_pluginDefinition);

		addAndStartPlugin(plugin);
	}

	private void addAndStartPlugin(final IPlugin _plugin) {
		pluginLibrary.addPlugin(_plugin);
		LOGGER.info("Starting Plugin {}", _plugin.getDefinition().getName());
		for (final IPluginIntegrator pluginIntegrator : integrators) {
			pluginIntegrator.pluginStarted(_plugin);
		}
	}

}
