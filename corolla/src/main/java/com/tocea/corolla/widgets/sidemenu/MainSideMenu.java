/**
 *
 */
package com.tocea.corolla.widgets.sidemenu;

import com.tocea.corolla.views.Homepage;
import com.tocea.corolla.views.admin.central.AdminCentralPage;

/**
 * This class defines the main menu panel.
 *
 * @author sleroy
 *
 */
public class MainSideMenu extends SideMenuPanel {

	public MainSideMenu() {
		super("sidemenu");

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		this.addMenuItem(AdminCentralPage.class, "glyphicon-dashboard", "Administration");
		this.addMenuItem(Homepage.class, "glyphicon-home", "Home");
		this.addMenuItem(AdminCentralPage.class, "glyphicon-book", "Application management");
		this.addMenuItem(AdminCentralPage.class, "glyphicon-briefcase", "Requirements");
		this.addMenuItem(AdminCentralPage.class, "glyphicon-modal-window", "Test specifications");
		this.addMenuItem(AdminCentralPage.class, "glyphicon-tent", "Test campaigns");
	}


}
