/**
 *
 */
package com.tocea.corolla.widgets.sidemenu;

import com.tocea.corolla.views.Homepage;
import com.tocea.corolla.views.admin.central.AdminCentralPage;
import com.tocea.corolla.views.admin.roles.RoleAdminPage;
import com.tocea.corolla.views.admin.users.UserAdminPage;

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
		this.addMenuItem(RoleAdminPage.class, "glyphicon-briefcase", "Role management");
		this.addMenuItem(AdminCentralPage.class, "glyphicon-modal-window", "Configuration");
	}



}
