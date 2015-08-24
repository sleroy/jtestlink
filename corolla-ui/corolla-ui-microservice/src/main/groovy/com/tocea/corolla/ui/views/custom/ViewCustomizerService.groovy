package com.tocea.corolla.ui.views.custom

import groovy.util.logging.Slf4j

import javax.annotation.PostConstruct

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.tocea.corolla.core.plugins.api.IPlugin
import com.tocea.corolla.core.plugins.api.IPluginIntegrator
import com.tocea.corolla.core.plugins.api.IPluginRegistrationService
import com.tocea.corolla.core.plugins.model.SideMenuAction
import com.tocea.corolla.core.plugins.model.SideMenuItem

@Slf4j
@Service(value="customView")
public class ViewCustomizerService implements IPluginIntegrator {
	def LibraryRegistry jsRegistry = new LibraryRegistry()
	def LibraryRegistry cssRegistry = new LibraryRegistry()
	
	@Autowired
	def IPluginRegistrationService pluginRegistrationService
	
	def List<SideMenuItem> extraMenuItems = new ArrayList<>()
	def List<SideMenuAction> extraProjectItems = new ArrayList<>()
	
	
	
	@PostConstruct
	public void init() {
		log.info "View customizer listenings for plugins"
		pluginRegistrationService.registerIntegrator this
	}
	def void pluginDestroyed(IPlugin _plugin) {
		//TODO:: remove jsLibrary
	}
	
	/**
	 * Proceed to the integration of plugins.
	 */
	def void pluginStarted(IPlugin _plugin) {
		jsRegistry.addPaths _plugin.definition.resources.jsResources
		cssRegistry.addPaths _plugin.definition.resources.cssResources
		extraMenuItems.addAll _plugin.definition.sideMenuItems
		extraProjectItems.addAll _plugin.definition.projectMenuItems
	}
	
	def List<String> getJsLibraries() {
		return jsRegistry.pathLibrary
	}
	
	def List<String> getCssLibraries() {
		return cssRegistry.pathLibrary
	}
	
	def String toString() {
		return "Corolla View customizer"
	}
	
	def List<SideMenuItem> getSideMenuItems() {
		return extraMenuItems
	}
	
	def List<SideMenuAction> getProjectItems() {
		return extraProjectItems
	}
}
