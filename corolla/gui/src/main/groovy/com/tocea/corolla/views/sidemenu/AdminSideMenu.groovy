/**
 *
 */
package com.tocea.corolla.views.sidemenu

import com.tocea.corolla.views.HomePage
import com.tocea.corolla.views.admin.central.AdminCentralPage
import com.tocea.corolla.views.admin.roles.RoleAdminPage
import com.tocea.corolla.views.admin.users.UserAdminPage
import com.tocea.corolla.widgets.sidemenu.SideMenuPanel

/**
 * This class defines the main menu panel.
 *
 * @author sleroy
 *
 */
class AdminSideMenu extends SideMenuPanel {

	def AdminSideMenu() {
		super("sidemenu")
	}

	protected void onInitialize() {
		super.onInitialize()
		this.addMenuItem HomePage.class, "glyphicon-home", "Home"
		this.addMenuItem UserAdminPage.class, "glyphicon-book", "User management"
		this.addMenuItem RoleAdminPage.class, "glyphicon-briefcase", "Role management"
		this.addMenuItem AdminCentralPage.class, "glyphicon-modal-window", "Configuration"
	}
}
