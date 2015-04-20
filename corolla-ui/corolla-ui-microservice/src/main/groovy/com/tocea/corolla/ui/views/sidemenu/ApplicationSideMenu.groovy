/**
 *
 */
package com.tocea.corolla.ui.views.sidemenu

import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree

import com.tocea.corolla.ui.rest.api.IRestAPI
import com.tocea.corolla.ui.views.application.ApplicationTreeProvider
import com.tocea.corolla.ui.widgets.sidemenu.SideMenuPanel

/**
 * This class defines the application menu with tree.
 *
 * @author sleroy
 *
 */
public class ApplicationSideMenu extends SideMenuPanel {

	IRestAPI restAPI

	ApplicationSideMenu(IRestAPI _restAPI) {
		restAPI = _restAPI
	}



	@Override
	protected void onInitialize() {
		super.onInitialize()

	}

	def addCustomPanel() {
		def treeProvider = new ApplicationTreeProvider(restAPI)
		def tree = new DefaultNestedTree("customPanel", treeProvider)
		add(tree)
	}
}
