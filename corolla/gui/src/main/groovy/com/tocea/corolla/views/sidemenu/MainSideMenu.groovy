/**
 *
 */
package com.tocea.corolla.views.sidemenu

import com.tocea.corolla.views.HomePage
import com.tocea.corolla.views.admin.central.AdminCentralPage
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel

/**
 * This class defines the main menu panel.
 *
 * @author sleroy
 *
 */
public class MainSideMenu extends SideMenuPanel {

	MainSideMenu() {
		super("sidemenu")
	}

	@Override
	protected void onInitialize() {
		super.onInitialize()
		this.addMenuItem AdminCentralPage.class, "glyphicon-dashboard", "Administration"
		this.addMenuItem HomePage.class, "glyphicon-home", "Home"
		this.addMenuItem AdminCentralPage.class, "glyphicon-book", "Application management"
		this.addMenuItem AdminCentralPage.class, "glyphicon-briefcase", "Requirements"
		this.addMenuItem AdminCentralPage.class, "glyphicon-modal-window", "Test specifications"
		this.addMenuItem AdminCentralPage.class, "glyphicon-tent", "Test campaigns"
	}
}
