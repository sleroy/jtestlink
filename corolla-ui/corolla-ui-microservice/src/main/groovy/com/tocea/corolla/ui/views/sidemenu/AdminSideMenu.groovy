/**
 *
 */
package com.tocea.corolla.ui.views.sidemenu

import com.tocea.corolla.ui.views.HomePage
import com.tocea.corolla.ui.views.admin.central.AdminCentralPage
import com.tocea.corolla.ui.views.admin.roles.RoleAdminPage
import com.tocea.corolla.ui.views.admin.users.UserAdminPage
import com.tocea.corolla.ui.widgets.sidemenu.SideMenuPanel

/**
 * This class defines the main menu panel.
 *
 * @author sleroy
 *
 */
class AdminSideMenu extends SideMenuPanel {


	protected void onInitialize() {
		super.onInitialize()
		this.addMenuItem HomePage.class, "glyphicon-home", "Home"
		this.addMenuItem UserAdminPage.class, "glyphicon-book", "User management"
		this.addMenuItem RoleAdminPage.class, "glyphicon-briefcase", "Role management"
		this.addMenuItem AdminCentralPage.class, "glyphicon-modal-window", "Configuration"
	}
}
