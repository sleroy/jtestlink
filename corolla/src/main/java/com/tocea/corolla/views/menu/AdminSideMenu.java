/**
 *
 */
package com.tocea.corolla.views.menu;

import com.tocea.corolla.views.Homepage;
import com.tocea.corolla.views.admin.central.AdminCentralPage;
import com.tocea.corolla.views.admin.users.UserAdminPage;
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel;

/**
 * This class defines the main menu panel.
 *
 * @author sleroy
 *
 */
public class AdminSideMenu extends SideMenuPanel {

	public AdminSideMenu() {
		super("sidemenu");

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		this.addMenuItem(Homepage.class, "glyphicon-home", "Home");
		this.addMenuItem(UserAdminPage.class, "glyphicon-book", "User management");
		this.addMenuItem(AdminCentralPage.class, "glyphicon-briefcase", "Role management");
		this.addMenuItem(AdminCentralPage.class, "glyphicon-modal-window", "Configuration");
	}



}
