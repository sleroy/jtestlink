package com.tocea.corolla.core.plugins.model;

import java.io.File;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

public class gni {
	
	public static void main(final String[] args) {
		final PluginDefinition pluginDefinition = new PluginDefinition();
		pluginDefinition.setAuthor("Sylvain Leroy");
		pluginDefinition.setKey("demoplugin");
		pluginDefinition.setName("Demo plugin");
		pluginDefinition.setDescription("This plugin is loaded automatically to populate the plugin registry");
		pluginDefinition.setCategory("Internal");
		pluginDefinition.getResources().addJSResource("jquery.js");
		pluginDefinition.setHasAdminSection(true);
		pluginDefinition.setUrl("http://sleroy/demoPlugin");
		final SideMenuAction sideMenu = new SideMenuAction();
		sideMenu.setGlyphicon("glyph");
		sideMenu.setName("Action 1");
		sideMenu.setUri("uri://");
		final SideMenuItem item = new SideMenuItem();
		item.setActions(Collections.singletonList(sideMenu));

		pluginDefinition.setSideMenuItems(Collections.singletonList(item));
		try {
			final JAXBContext context = JAXBContext.newInstance(PluginDefinition.class);
			
			final Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			m.marshal(pluginDefinition, new File("src/main/resources/plugin.xml"));
		} catch (final PropertyException e) {
			e.printStackTrace();
		} catch (final JAXBException e) {
			e.printStackTrace();
		}
	}
	
}
