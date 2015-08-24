package com.tocea.corolla.core.plugins.system;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tocea.corolla.core.plugins.api.IPluginRegistrationService;
import com.tocea.corolla.core.plugins.model.PluginDefinition;

/**
 * This class checks for the presence of XML COrolla plugin loaded in the
 * classpath and adds them to the container.
 *
 * @author sleroy
 *         
 */
@Service
public class XMLPluginRegistrationService implements ApplicationContextAware {

	private static final String META_INF_PLUGIN_XML = "META-INF/plugin.xml";

	private static final Logger LOGGER = LoggerFactory.getLogger(XMLPluginRegistrationService.class);

	private ApplicationContext applicationContext;

	@Autowired
	private IPluginRegistrationService pluginRegistrationService;

	@PostConstruct
	public void initializeXmlPlugins() {
		try {
			LOGGER.info("Loading XML Plugin inside Corolla");
			final Enumeration<URL> resources = Thread.currentThread().getContextClassLoader()
					.getResources(META_INF_PLUGIN_XML);

			while (resources.hasMoreElements()) {
				final URL nextElement = resources.nextElement();
				try {

					pluginRegistrationService.registerPlugin(PluginDefinition.loadFromXML(nextElement));
				} catch (final Exception e) {
					LOGGER.error("Could not load the plugin defined in resource {}", nextElement, e);
				}
			}
		} catch (final IOException e) {
			LOGGER.info("Could not retried any plugin definition", e);
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext _applicationContext) throws BeansException {
		applicationContext = _applicationContext;

	}

}
