/**
 *
 */
package com.tocea.corolla.views.sidemenu

import org.apache.wicket.extensions.markup.html.repeater.tree.DefaultNestedTree

import com.tocea.corolla.views.api.IViewAPI
import com.tocea.corolla.views.application.ApplicationTreeProvider
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel

/**
 * This class defines the application menu with tree.
 *
 * @author sleroy
 *
 */
public class ApplicationSideMenu extends SideMenuPanel {

	IViewAPI viewAPI

	ApplicationSideMenu(IViewAPI _viewAPI) {
		viewAPI = _viewAPI
	}



	@Override
	protected void onInitialize() {
		super.onInitialize()

	}

	def addCustomPanel() {
		def treeProvider = new ApplicationTreeProvider(viewAPI)
		def tree = new DefaultNestedTree("customPanel", treeProvider)
		add(tree)
	}
}
